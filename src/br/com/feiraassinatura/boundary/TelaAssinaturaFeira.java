package br.com.feiraassinatura.boundary;

import br.com.feiraassinatura.controller.AssinaturaController;
import br.com.feiraassinatura.domain.CartaoCredito;
import br.com.feiraassinatura.domain.CestaSemanal;
import br.com.feiraassinatura.domain.EnderecoEntrega;
import br.com.feiraassinatura.domain.ItemCesta;
import br.com.feiraassinatura.domain.PlanoAssinatura;
import br.com.feiraassinatura.domain.Produto;
import br.com.feiraassinatura.domain.TipoProduto;
import br.com.feiraassinatura.dto.ConfirmacaoAssinatura;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class TelaAssinaturaFeira {
    private final AssinaturaController controller;
    private final Scanner scanner;

    public TelaAssinaturaFeira(AssinaturaController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        System.out.println("==========================================");
        System.out.println(" Assinar Servico de Feira");
        System.out.println("==========================================");

        String celular = capturarCelular();
        controller.iniciarCadastro(celular);
        capturarCodigoSms(celular);

        PlanoAssinatura plano = escolherPlano();
        for (TipoProduto tipo : TipoProduto.values()) {
            escolherItens(tipo, plano);
        }

        exibirResumoCesta(controller.getCestaAtual());
        double total = confirmarEntrega();
        System.out.printf(Locale.US, "%nTotal semanal da assinatura: R$ %.2f%n", total);

        ConfirmacaoAssinatura confirmacao = confirmarPagamento();
        exibirConfirmacao(confirmacao);
    }

    public String capturarCelular() {
        return lerTextoObrigatorio("Informe o numero de celular com DDD: ");
    }

    public void capturarCodigoSms(String celular) {
        while (true) {
            String codigo = lerTextoObrigatorio("Informe o codigo recebido por SMS: ");
            if (controller.confirmarCodigo(celular, codigo)) {
                System.out.println("Codigo confirmado com sucesso.");
                return;
            }
            System.out.println("Codigo invalido ou expirado. Tente novamente.");
        }
    }

    public void exibirPlanos(List<PlanoAssinatura> planos) {
        System.out.println();
        System.out.println("Planos de assinatura disponiveis:");
        for (PlanoAssinatura plano : planos) {
            System.out.printf(
                    Locale.US,
                    "%d - %s | R$ %.2f/semana | frutas: %d, legumes: %d, verduras: %d | %s%n",
                    plano.getId(),
                    plano.getNome(),
                    plano.getPreco(),
                    plano.getLimiteFrutas(),
                    plano.getLimiteLegumes(),
                    plano.getLimiteVerduras(),
                    plano.getDescricao());
        }
    }

    public PlanoAssinatura capturarPlano() {
        while (true) {
            try {
                long idPlano = lerLong("Selecione o codigo do plano desejado: ");
                return controller.selecionarPlano(idPlano);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void exibirProdutos(TipoProduto tipo, List<Produto> produtos) {
        System.out.println();
        System.out.println("Itens disponiveis de " + tipo.getDescricaoPlural() + ":");
        for (Produto produto : produtos) {
            System.out.printf(
                    "%d - %s (disponivel: %d)%n",
                    produto.getId(),
                    produto.getNome(),
                    produto.getQuantidadeDisponivel());
        }
    }

    public Map<Long, Integer> capturarItens(TipoProduto tipo, List<Produto> produtos, int limite) {
        Map<Long, Integer> escolhas = new LinkedHashMap<>();
        System.out.println("Escolha ate " + limite + " item(ns) de " + tipo.getDescricaoPlural() + ".");
        for (Produto produto : produtos) {
            int quantidade = lerQuantidadeProduto(produto);
            if (quantidade > 0) {
                escolhas.put(produto.getId(), quantidade);
            }
        }
        return escolhas;
    }

    public void exibirResumoCesta(CestaSemanal cesta) {
        System.out.println();
        System.out.println("Resumo da cesta da semana:");
        for (ItemCesta item : cesta.getItens()) {
            System.out.printf(
                    "- %s: %s x%d%n",
                    item.getProduto().getTipo(),
                    item.getProduto().getNome(),
                    item.getQuantidade());
        }
    }

    public EnderecoEntrega capturarEndereco() {
        System.out.println();
        System.out.println("Endereco de entrega:");
        String cep = lerTextoObrigatorio("CEP: ");
        String rua = lerTextoObrigatorio("Rua: ");
        String numero = lerTextoObrigatorio("Numero: ");
        String bairro = lerTextoObrigatorio("Bairro: ");
        String cidade = lerTextoObrigatorio("Cidade: ");
        String complemento = lerTextoOpcional("Complemento: ");
        return new EnderecoEntrega(cep, rua, numero, bairro, cidade, complemento);
    }

    public CartaoCredito capturarDadosCartao() {
        System.out.println();
        System.out.println("Dados do cartao de credito:");
        String numero = lerTextoObrigatorio("Numero do cartao: ");
        String titular = lerTextoObrigatorio("Nome do titular: ");
        String validade = lerTextoObrigatorio("Validade (MM/AA): ");
        String bandeira = lerTextoObrigatorio("Bandeira: ");
        return CartaoCredito.criar(numero, titular, validade, bandeira);
    }

    public void exibirConfirmacao(ConfirmacaoAssinatura confirmacao) {
        System.out.println();
        System.out.println("Operacao realizada com sucesso.");
        System.out.println("Status da assinatura: " + confirmacao.getAssinatura().getStatus());
        System.out.println("Status da cesta: " + confirmacao.getAssinatura().getCesta().getStatus());
        System.out.println("Entrega: " + confirmacao.getEnderecoEntrega().resumo());
        System.out.println("Proxima entrega: " + confirmacao.getAssinatura().getProximaEntrega());
        System.out.println("Protocolo: " + confirmacao.getProtocolo().getNumero());
    }

    private PlanoAssinatura escolherPlano() {
        List<PlanoAssinatura> planos = controller.listarPlanos();
        exibirPlanos(planos);
        return capturarPlano();
    }

    private void escolherItens(TipoProduto tipo, PlanoAssinatura plano) {
        while (true) {
            try {
                List<Produto> produtos = controller.listarProdutosPorTipo(tipo);
                exibirProdutos(tipo, produtos);
                Map<Long, Integer> itens = capturarItens(tipo, produtos, plano.limitePara(tipo));
                controller.confirmarItens(tipo, itens);
                System.out.println("Itens de " + tipo.getDescricaoPlural() + " armazenados na cesta.");
                return;
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private double confirmarEntrega() {
        while (true) {
            try {
                EnderecoEntrega endereco = capturarEndereco();
                return controller.confirmarEntrega(endereco);
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private ConfirmacaoAssinatura confirmarPagamento() {
        while (true) {
            try {
                CartaoCredito cartao = capturarDadosCartao();
                return controller.confirmarPagamento(cartao);
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private int lerQuantidadeProduto(Produto produto) {
        while (true) {
            int quantidade = lerInt("Quantidade de " + produto.getNome() + " (0 para nao escolher): ");
            if (quantidade < 0) {
                System.out.println("A quantidade nao pode ser negativa.");
            } else if (quantidade > produto.getQuantidadeDisponivel()) {
                System.out.println("Quantidade maior que a disponibilidade da semana.");
            } else {
                return quantidade;
            }
        }
    }

    private long lerLong(String prompt) {
        while (true) {
            String valor = lerTextoObrigatorio(prompt);
            try {
                return Long.parseLong(valor);
            } catch (NumberFormatException exception) {
                System.out.println("Informe um numero valido.");
            }
        }
    }

    private int lerInt(String prompt) {
        while (true) {
            String valor = lerTextoObrigatorio(prompt);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException exception) {
                System.out.println("Informe um numero inteiro valido.");
            }
        }
    }

    private String lerTextoObrigatorio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = scanner.nextLine().trim();
            if (!valor.isBlank()) {
                return valor;
            }
            System.out.println("Campo obrigatorio.");
        }
    }

    private String lerTextoOpcional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
