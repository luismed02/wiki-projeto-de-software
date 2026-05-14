package br.com.feiraassinatura.controller;

import br.com.feiraassinatura.domain.Assinante;
import br.com.feiraassinatura.domain.Assinatura;
import br.com.feiraassinatura.domain.CartaoCredito;
import br.com.feiraassinatura.domain.CestaSemanal;
import br.com.feiraassinatura.domain.EnderecoEntrega;
import br.com.feiraassinatura.domain.Pagamento;
import br.com.feiraassinatura.domain.PlanoAssinatura;
import br.com.feiraassinatura.domain.Produto;
import br.com.feiraassinatura.domain.ProtocoloAssinatura;
import br.com.feiraassinatura.domain.StatusAssinatura;
import br.com.feiraassinatura.domain.TipoProduto;
import br.com.feiraassinatura.dto.ConfirmacaoAssinatura;
import br.com.feiraassinatura.repository.AssinaturaRepository;
import br.com.feiraassinatura.repository.ProtocoloRepository;
import br.com.feiraassinatura.service.AutenticacaoSmsService;
import br.com.feiraassinatura.service.CatalogoSemanaService;
import br.com.feiraassinatura.service.CestaSemanalService;
import br.com.feiraassinatura.service.EnderecoEntregaService;
import br.com.feiraassinatura.service.PagamentoService;
import br.com.feiraassinatura.service.PlanoAssinaturaService;
import br.com.feiraassinatura.service.ProtocoloService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AssinaturaController {
    private final AutenticacaoSmsService autenticacaoService;
    private final PlanoAssinaturaService planoService;
    private final CatalogoSemanaService catalogoService;
    private final CestaSemanalService cestaService;
    private final EnderecoEntregaService enderecoService;
    private final PagamentoService pagamentoService;
    private final ProtocoloService protocoloService;
    private final AssinaturaRepository assinaturaRepository;
    private final ProtocoloRepository protocoloRepository;

    private Assinante assinanteAtual;
    private PlanoAssinatura planoSelecionado;
    private CestaSemanal cestaAtual;
    private Assinatura assinaturaAtual;
    private EnderecoEntrega enderecoAtual;

    public AssinaturaController(
            AutenticacaoSmsService autenticacaoService,
            PlanoAssinaturaService planoService,
            CatalogoSemanaService catalogoService,
            CestaSemanalService cestaService,
            EnderecoEntregaService enderecoService,
            PagamentoService pagamentoService,
            ProtocoloService protocoloService,
            AssinaturaRepository assinaturaRepository,
            ProtocoloRepository protocoloRepository) {
        this.autenticacaoService = autenticacaoService;
        this.planoService = planoService;
        this.catalogoService = catalogoService;
        this.cestaService = cestaService;
        this.enderecoService = enderecoService;
        this.pagamentoService = pagamentoService;
        this.protocoloService = protocoloService;
        this.assinaturaRepository = assinaturaRepository;
        this.protocoloRepository = protocoloRepository;
    }

    public void iniciarCadastro(String celular) {
        autenticacaoService.enviarCodigoConfirmacao(celular);
    }

    public boolean confirmarCodigo(String celular, String codigo) {
        boolean confirmado = autenticacaoService.validarCodigo(celular, codigo);
        if (confirmado) {
            assinanteAtual = autenticacaoService.obterOuCriarAssinante(celular);
        }
        return confirmado;
    }

    public List<PlanoAssinatura> listarPlanos() {
        exigirAssinanteAutenticado();
        return planoService.listarPlanosDisponiveis();
    }

    public PlanoAssinatura selecionarPlano(long idPlano) {
        exigirAssinanteAutenticado();
        planoSelecionado = planoService.obterPlano(idPlano);
        cestaAtual = cestaService.criarCestaSemanal(planoSelecionado);
        return planoSelecionado;
    }

    public List<Produto> listarProdutosPorTipo(TipoProduto tipo) {
        exigirPlanoSelecionado();
        return catalogoService.listarItensDisponiveis(tipo, planoSelecionado);
    }

    public CestaSemanal confirmarItens(TipoProduto tipo, Map<Long, Integer> itens) {
        exigirPlanoSelecionado();
        if (cestaAtual == null) {
            throw new IllegalStateException("A cesta ainda nao foi criada.");
        }
        Map<Produto, Integer> itensValidados = catalogoService.validarDisponibilidade(tipo, itens);
        cestaService.adicionarItens(cestaAtual, tipo, itensValidados, planoSelecionado);
        return cestaAtual;
    }

    public double confirmarEntrega(EnderecoEntrega endereco) {
        exigirPlanoSelecionado();
        if (cestaAtual == null) {
            throw new IllegalStateException("A cesta ainda nao foi criada.");
        }
        enderecoAtual = enderecoService.validarEndereco(endereco);
        cestaService.confirmarCesta(cestaAtual);

        assinaturaAtual = new Assinatura(
                assinaturaRepository.proximoId(),
                assinanteAtual,
                planoSelecionado,
                cestaAtual,
                enderecoAtual,
                LocalDate.now(),
                StatusAssinatura.AGUARDANDO_APROVACAO,
                LocalDate.now().plusWeeks(1));
        assinaturaAtual.aguardarAprovacao();
        assinaturaRepository.salvar(assinaturaAtual);
        enderecoService.registrarEndereco(assinaturaAtual, enderecoAtual);
        return planoService.calcularTotal(planoSelecionado);
    }

    public ConfirmacaoAssinatura confirmarPagamento(CartaoCredito cartao) {
        if (assinaturaAtual == null) {
            throw new IllegalStateException("Confirme a entrega antes de informar o pagamento.");
        }
        Pagamento pagamento = pagamentoService.processarPagamento(assinaturaAtual, cartao);
        assinaturaAtual.registrarPagamento(pagamento);
        assinaturaAtual.aprovar();
        assinaturaRepository.atualizarStatus(assinaturaAtual, assinaturaAtual.getStatus());

        cestaService.aprovarCesta(cestaAtual);

        ProtocoloAssinatura protocolo = protocoloService.gerarProtocolo(assinaturaAtual);
        assinaturaAtual.registrarProtocolo(protocolo.getNumero());
        assinaturaRepository.salvarProtocolo(assinaturaAtual, protocolo.getNumero());
        protocoloRepository.salvar(assinaturaAtual.getId(), protocolo);
        return new ConfirmacaoAssinatura(assinaturaAtual, enderecoAtual, protocolo);
    }

    public CestaSemanal getCestaAtual() {
        return cestaAtual;
    }

    private void exigirAssinanteAutenticado() {
        if (assinanteAtual == null) {
            throw new IllegalStateException("Confirme o codigo SMS antes de continuar.");
        }
    }

    private void exigirPlanoSelecionado() {
        exigirAssinanteAutenticado();
        if (planoSelecionado == null) {
            throw new IllegalStateException("Selecione um plano antes de continuar.");
        }
    }
}
