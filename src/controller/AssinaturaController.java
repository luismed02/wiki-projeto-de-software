package controller;

import domain.*;
import repository.*;
import service.*;
import java.time.LocalDate;
import java.util.List;

public class AssinaturaController {
    private final AutenticacaoSmsService autenticacao;
    private final PlanoAssinaturaService planoService;
    private final CatalogoSemanaService catalogoService;
    private final CestaSemanalService cestaService;
    private final EnderecoEntregaService enderecoService;
    private final PagamentoService pagamentoService;
    private final ProtocoloService protocoloService;
    private final AssinanteRepository assinanteRepo;
    private final AssinaturaRepository assinaturaRepo;

    private String celularAtual;
    private Assinante assinante;
    private PlanoAssinatura planoSelecionado;
    private CestaSemanal cesta;
    private Assinatura assinatura;
    private EnderecoEntrega endereco;
    private String protocolo;
    private Pagamento pagamento;

    public AssinaturaController(AutenticacaoSmsService autenticacao,
                                PlanoAssinaturaService planoService,
                                CatalogoSemanaService catalogoService,
                                CestaSemanalService cestaService,
                                EnderecoEntregaService enderecoService,
                                PagamentoService pagamentoService,
                                ProtocoloService protocoloService,
                                AssinanteRepository assinanteRepo,
                                AssinaturaRepository assinaturaRepo) {
        this.autenticacao = autenticacao;
        this.planoService = planoService;
        this.catalogoService = catalogoService;
        this.cestaService = cestaService;
        this.enderecoService = enderecoService;
        this.pagamentoService = pagamentoService;
        this.protocoloService = protocoloService;
        this.assinanteRepo = assinanteRepo;
        this.assinaturaRepo = assinaturaRepo;
    }

    public void iniciarCadastro(String celular) {
        this.celularAtual = celular;
        autenticacao.enviarCodigoConfirmacao(celular);
    }

    public boolean confirmarCodigo(String celular, String codigo) {
        boolean ok = autenticacao.validarCodigo(celular, codigo);
        if (ok) {
            assinante = assinanteRepo.buscarPorCelular(celular);
            if (assinante == null) {
                assinante = assinanteRepo.salvar(new Assinante(null, "Assinante " + celular, celular, "", LocalDate.now()));
            }
        }
        return ok;
    }

    public List<PlanoAssinatura> listarPlanos() {
        return planoService.listarPlanosDisponiveis();
    }

    public PlanoAssinatura selecionarPlano(Long idPlano) {
        planoSelecionado = planoService.obterPlano(idPlano);
        cesta = cestaService.criarCestaSemanal(planoSelecionado);
        return planoSelecionado;
    }

    public List<Produto> listarProdutosPorTipo(TipoProduto tipo) {
        return catalogoService.listarItensDisponiveis(tipo, planoSelecionado);
    }

    public int limitePara(TipoProduto tipo) {
        return planoSelecionado.limitePara(tipo);
    }

    public void confirmarItens(TipoProduto tipo, List<ItemCesta> itens) {
        cestaService.adicionarItens(cesta, itens);
    }

    public CestaSemanal getCesta() {
        return cesta;
    }

    public PlanoAssinatura getPlanoSelecionado() {
        return planoSelecionado;
    }

    public void confirmarEntrega(EnderecoEntrega enderecoInformado) {
        if (!enderecoService.validarEndereco(enderecoInformado)) {
            throw new IllegalArgumentException("Endereço inválido. Campos obrigatórios: cep, rua, numero, bairro, cidade.");
        }
        cestaService.confirmarCesta(cesta);
        assinatura = new Assinatura(assinante.id, planoSelecionado.id, cesta.id);
        assinatura = assinaturaRepo.salvar(assinatura);
        endereco = enderecoService.registrarEndereco(assinatura.id, enderecoInformado);
    }

    public double totalAssinatura() {
        return planoService.calcularTotal(planoSelecionado);
    }

    public String confirmarPagamento(CartaoCredito cartao) {
        pagamento = pagamentoService.processarPagamento(assinatura, totalAssinatura(), cartao);
        if (pagamento.status != StatusPagamento.APROVADO) {
            throw new RuntimeException("Pagamento recusado pela operadora.");
        }
        assinaturaRepo.atualizarStatus(assinatura, StatusAssinatura.APROVADA);
        cestaService.aprovarCesta(cesta);
        protocolo = protocoloService.gerarProtocolo(assinatura);
        return protocolo;
    }

    public EnderecoEntrega getEndereco() { return endereco; }
    public String getProtocolo() { return protocolo; }
    public Pagamento getPagamento() { return pagamento; }
}
