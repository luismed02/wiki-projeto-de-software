package boundary;

import controller.AssinaturaController;
import domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaAssinaturaFeira {
    private final AssinaturaController controller;
    private final Scanner sc = new Scanner(System.in);

    public TelaAssinaturaFeira(AssinaturaController controller) {
        this.controller = controller;
    }

    public void iniciar() {
        System.out.println("=== Assinatura de Serviço de Feira ===");

        String celular = capturarCelular();
        controller.iniciarCadastro(celular);

        String codigo = capturarCodigoSms();
        if (!controller.confirmarCodigo(celular, codigo)) {
            System.out.println("Código inválido. Encerrando.");
            return;
        }
        System.out.println("Autenticação confirmada.\n");

        List<PlanoAssinatura> planos = controller.listarPlanos();
        exibirPlanos(planos);
        Long idPlano = capturarPlano(planos);
        PlanoAssinatura plano = controller.selecionarPlano(idPlano);
        System.out.println("Plano selecionado: " + plano.nome + " (R$ " + plano.precoSemanal + ")\n");

        for (TipoProduto tipo : TipoProduto.values()) {
            List<Produto> produtos = controller.listarProdutosPorTipo(tipo);
            int limite = controller.limitePara(tipo);
            exibirProdutos(tipo, produtos, limite);
            List<ItemCesta> itens = capturarItens(tipo, produtos, limite);
            controller.confirmarItens(tipo, itens);
        }

        exibirResumoCesta(controller.getCesta());
        EnderecoEntrega endereco = capturarEndereco();
        try {
            controller.confirmarEntrega(endereco);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
        System.out.println("Cesta confirmada. Status da assinatura: AGUARDANDO_APROVACAO\n");

        double total = controller.totalAssinatura();
        System.out.println("Total da assinatura: R$ " + total);
        CartaoCredito cartao = capturarDadosCartao();
        try {
            String protocolo = controller.confirmarPagamento(cartao);
            exibirConfirmacao(protocolo, controller.getEndereco());
        } catch (RuntimeException e) {
            System.out.println("Erro no pagamento: " + e.getMessage());
        }
    }

    private String capturarCelular() {
        System.out.print("Informe seu celular (DDD + número): ");
        return sc.nextLine().trim();
    }

    private String capturarCodigoSms() {
        System.out.print("Digite o código recebido por SMS: ");
        return sc.nextLine().trim();
    }

    private void exibirPlanos(List<PlanoAssinatura> planos) {
        System.out.println("Planos disponíveis:");
        for (PlanoAssinatura p : planos) {
            System.out.printf("  [%d] %s - R$ %.2f - %s (limites: %d frutas, %d legumes, %d verduras)%n",
                    p.id, p.nome, p.precoSemanal, p.descricao, p.limiteFrutas, p.limiteLegumes, p.limiteVerduras);
        }
    }

    private Long capturarPlano(List<PlanoAssinatura> planos) {
        while (true) {
            System.out.print("Escolha o plano (id): ");
            try {
                long id = Long.parseLong(sc.nextLine().trim());
                for (PlanoAssinatura p : planos) {
                    if (p.id == id) return id;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Plano inválido.");
        }
    }

    private void exibirProdutos(TipoProduto tipo, List<Produto> produtos, int limite) {
        System.out.println("\n--- " + tipo + " (limite: " + limite + " itens) ---");
        for (Produto p : produtos) {
            System.out.printf("  [%d] %s (disponível: %d)%n", p.id, p.nome, p.quantidadeDisponivel);
        }
    }

    private List<ItemCesta> capturarItens(TipoProduto tipo, List<Produto> produtos, int limite) {
        List<ItemCesta> itens = new ArrayList<>();
        int totalEscolhido = 0;
        System.out.println("Escolha até " + limite + " itens. Formato: id quantidade (ex: 1 2). Digite 'fim' para terminar.");
        while (totalEscolhido < limite) {
            System.out.print("> ");
            String entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("fim")) break;
            String[] partes = entrada.split("\\s+");
            if (partes.length != 2) { System.out.println("Formato inválido."); continue; }
            try {
                long id = Long.parseLong(partes[0]);
                int qtd = Integer.parseInt(partes[1]);
                Produto p = produtos.stream().filter(x -> x.id == id).findFirst().orElse(null);
                if (p == null) { System.out.println("Produto não encontrado."); continue; }
                if (totalEscolhido + qtd > limite) {
                    System.out.println("Excede o limite do plano. Restam " + (limite - totalEscolhido) + " unidades.");
                    continue;
                }
                itens.add(new ItemCesta(null, id, qtd));
                totalEscolhido += qtd;
                System.out.println("Adicionado: " + p.nome + " x" + qtd + " (total " + totalEscolhido + "/" + limite + ")");
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido.");
            }
        }
        return itens;
    }

    private void exibirResumoCesta(CestaSemanal cesta) {
        System.out.println("\n=== Resumo da cesta ===");
        for (ItemCesta i : cesta.itens) {
            System.out.println("  produto #" + i.produtoId + " x" + i.quantidade);
        }
    }

    private EnderecoEntrega capturarEndereco() {
        System.out.println("\n--- Endereço de entrega ---");
        EnderecoEntrega e = new EnderecoEntrega();
        System.out.print("CEP: "); e.cep = sc.nextLine().trim();
        System.out.print("Rua: "); e.rua = sc.nextLine().trim();
        System.out.print("Número: "); e.numero = sc.nextLine().trim();
        System.out.print("Bairro: "); e.bairro = sc.nextLine().trim();
        System.out.print("Cidade: "); e.cidade = sc.nextLine().trim();
        System.out.print("Complemento (opcional): "); e.complemento = sc.nextLine().trim();
        return e;
    }

    private CartaoCredito capturarDadosCartao() {
        System.out.println("\n--- Dados do cartão ---");
        CartaoCredito c = new CartaoCredito();
        System.out.print("Número do cartão: ");
        String num = sc.nextLine().trim().replaceAll("\\s+", "");
        c.numeroMascarado = num.length() >= 4 ? "**** **** **** " + num.substring(num.length() - 4) : "****";
        System.out.print("Nome do titular: "); c.nomeTitular = sc.nextLine().trim();
        System.out.print("Validade (MM/AA): "); c.validade = sc.nextLine().trim();
        System.out.print("Bandeira: "); c.bandeira = sc.nextLine().trim();
        return c;
    }

    private void exibirConfirmacao(String protocolo, EnderecoEntrega endereco) {
        System.out.println();
        System.out.println("=== Assinatura realizada com sucesso ===");
        System.out.println("Protocolo: " + protocolo);
        System.out.println("Entrega em: " + endereco.rua + ", " + endereco.numero + " - " + endereco.bairro + ", " + endereco.cidade + " (CEP " + endereco.cep + ")");
        System.out.println("Status da assinatura: APROVADA");
        System.out.println("Status da cesta: APROVADA");
        System.out.println("Status do pagamento: APROVADO");
    }
}
