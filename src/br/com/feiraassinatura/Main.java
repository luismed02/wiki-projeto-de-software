package br.com.feiraassinatura;

import br.com.feiraassinatura.boundary.TelaAssinaturaFeira;
import br.com.feiraassinatura.controller.AssinaturaController;
import br.com.feiraassinatura.gateway.OperadoraCartaoCreditoGateway;
import br.com.feiraassinatura.gateway.SmsGateway;
import br.com.feiraassinatura.repository.AssinanteRepository;
import br.com.feiraassinatura.repository.AssinaturaRepository;
import br.com.feiraassinatura.repository.CatalogoProdutoRepository;
import br.com.feiraassinatura.repository.CestaSemanalRepository;
import br.com.feiraassinatura.repository.CodigoVerificacaoRepository;
import br.com.feiraassinatura.repository.EnderecoEntregaRepository;
import br.com.feiraassinatura.repository.PagamentoRepository;
import br.com.feiraassinatura.repository.PlanoAssinaturaRepository;
import br.com.feiraassinatura.repository.ProtocoloRepository;
import br.com.feiraassinatura.service.AutenticacaoSmsService;
import br.com.feiraassinatura.service.CatalogoSemanaService;
import br.com.feiraassinatura.service.CestaSemanalService;
import br.com.feiraassinatura.service.EnderecoEntregaService;
import br.com.feiraassinatura.service.PagamentoService;
import br.com.feiraassinatura.service.PlanoAssinaturaService;
import br.com.feiraassinatura.service.ProtocoloService;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path dataDir = Paths.get(System.getProperty("feira.dataDir", "src/data"));

        AssinanteRepository assinanteRepository = new AssinanteRepository(dataDir);
        CodigoVerificacaoRepository codigoRepository = new CodigoVerificacaoRepository(dataDir);
        PlanoAssinaturaRepository planoRepository = new PlanoAssinaturaRepository(dataDir);
        CatalogoProdutoRepository catalogoRepository = new CatalogoProdutoRepository(dataDir);
        CestaSemanalRepository cestaRepository = new CestaSemanalRepository(dataDir);
        AssinaturaRepository assinaturaRepository = new AssinaturaRepository(dataDir);
        EnderecoEntregaRepository enderecoRepository = new EnderecoEntregaRepository(dataDir);
        PagamentoRepository pagamentoRepository = new PagamentoRepository(dataDir);
        ProtocoloRepository protocoloRepository = new ProtocoloRepository(dataDir);

        SmsGateway smsGateway = new SmsGateway();
        OperadoraCartaoCreditoGateway operadoraGateway = new OperadoraCartaoCreditoGateway();

        AutenticacaoSmsService autenticacaoService = new AutenticacaoSmsService(
                smsGateway,
                codigoRepository,
                assinanteRepository);
        PlanoAssinaturaService planoService = new PlanoAssinaturaService(planoRepository);
        CatalogoSemanaService catalogoService = new CatalogoSemanaService(catalogoRepository);
        CestaSemanalService cestaService = new CestaSemanalService(cestaRepository);
        EnderecoEntregaService enderecoService = new EnderecoEntregaService(enderecoRepository);
        PagamentoService pagamentoService = new PagamentoService(operadoraGateway, pagamentoRepository);
        ProtocoloService protocoloService = new ProtocoloService();

        AssinaturaController controller = new AssinaturaController(
                autenticacaoService,
                planoService,
                catalogoService,
                cestaService,
                enderecoService,
                pagamentoService,
                protocoloService,
                assinaturaRepository,
                protocoloRepository);

        new TelaAssinaturaFeira(controller).executar();
    }
}
