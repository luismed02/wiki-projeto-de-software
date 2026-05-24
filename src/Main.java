import boundary.TelaAssinaturaFeira;
import controller.AssinaturaController;
import gateway.OperadoraCartaoCreditoGateway;
import gateway.SmsGateway;
import repository.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        // gateways
        SmsGateway smsGateway = new SmsGateway();
        OperadoraCartaoCreditoGateway operadoraGateway = new OperadoraCartaoCreditoGateway();

        // repositórios
        AssinanteRepository assinanteRepo = new AssinanteRepository();
        CodigoVerificacaoRepository codigoRepo = new CodigoVerificacaoRepository();
        PlanoAssinaturaRepository planoRepo = new PlanoAssinaturaRepository();
        CatalogoProdutoRepository catalogoRepo = new CatalogoProdutoRepository();
        AssinaturaRepository assinaturaRepo = new AssinaturaRepository();
        CestaSemanalRepository cestaRepo = new CestaSemanalRepository();
        EnderecoEntregaRepository enderecoRepo = new EnderecoEntregaRepository();
        PagamentoRepository pagamentoRepo = new PagamentoRepository();

        // serviços
        AutenticacaoSmsService autenticacao = new AutenticacaoSmsService(smsGateway, codigoRepo);
        PlanoAssinaturaService planoService = new PlanoAssinaturaService(planoRepo);
        CatalogoSemanaService catalogoService = new CatalogoSemanaService(catalogoRepo);
        CestaSemanalService cestaService = new CestaSemanalService(cestaRepo);
        EnderecoEntregaService enderecoService = new EnderecoEntregaService(enderecoRepo);
        PagamentoService pagamentoService = new PagamentoService(operadoraGateway, pagamentoRepo);
        ProtocoloService protocoloService = new ProtocoloService(assinaturaRepo);

        // controller
        AssinaturaController controller = new AssinaturaController(
                autenticacao, planoService, catalogoService, cestaService,
                enderecoService, pagamentoService, protocoloService,
                assinanteRepo, assinaturaRepo
        );

        // boundary
        new TelaAssinaturaFeira(controller).iniciar();
    }
}
