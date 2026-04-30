# Projeto de Software - Assinatura de Feira

## Navegação rápida

- [Visão geral](#visão-geral)
- [Fluxo do caso de uso](#fluxo-do-caso-de-uso)
- [Storyboard de telas](#storyboard-de-telas)
- [Diagrama de Classes de Domínio](#diagrama-de-classes-de-domínio)
- [Projeto do Caso de Uso: Assinar Serviço de Feira](#projeto-do-caso-de-uso-assinar-serviço-de-feira)
- [Diagrama de Sequência de Projeto](#diagrama-de-sequência-de-projeto)
- [Diagrama de Classes de Projeto](#diagrama-de-classes-de-projeto)

## Visão geral

Cada vez mais os serviços de compra por assinatura têm se popularizado. Neste projeto, considera-se um serviço de assinatura semanal de frutas, legumes e verduras, inspirado em soluções como Feira na Box e Frutas em Casa.

O cenário de uso principal é Assinar Serviço de Feira, no qual o assinante seleciona plano, monta a cesta semanal, informa endereço de entrega e realiza o pagamento com validação junto à operadora de cartão de crédito.

- Ator principal: Assinante
- Ator secundário: Operadora de Cartão de Crédito
- Objetivo: realizar a assinatura de um plano de entrega semanal de produtos comercializados em feiras livres para entrega no endereço definido pelo assinante
- Pré-condições: planos de assinatura exibidos e catálogo de produtos da semana atualizado
- Pós-condição: informações do assinante validadas e armazenadas, plano selecionado armazenado, endereço validado e armazenado, preferências de entrega definidas e cesta da semana confirmada com pagamento aprovado

## Fluxo do caso de uso

![Fluxo do caso de uso - Assinar Serviço de Feira](wiki/user_flow.png)

## Storyboard de telas

![Tela 1](wiki/telas/Frame%201.png)

⬇️

![Tela 2](wiki/telas/Frame%202.png)

⬇️

![Tela 3](wiki/telas/Frame%203.png)

⬇️

![Tela 4](wiki/telas/Frame%204.png)

⬇️

![Tela 5](wiki/telas/Frame%205.png)

⬇️

![Tela 6](wiki/telas/Frame%206.png)

⬇️

![Tela 7](wiki/telas/Frame%207.png)

⬇️

![Tela 8](wiki/telas/Frame%208.png)

⬇️

![Tela 9](wiki/telas/Frame%209.png)

⬇️

![Tela 10](wiki/telas/Frame%2010.png)

## Diagrama de Classes de Domínio

```mermaid
classDiagram
    class Assinante {
        +Long id
        +String nome
        +String celular
        +String email
        +Date dataCadastro
    }
    class CodigoVerificacao {
        +String codigo
        +DateTime dataEnvio
        +boolean utilizado
        +boolean expirado
    }
    class PlanoAssinatura {
        +Long id
        +String nome
        +String descricao
        +boolean ativo
    }
    class OpcaoPlanoAssinatura {
        +Long id
        +String nome
        +double precoSemanal
        +int limiteFrutas
        +int limiteLegumes
        +int limiteVerduras
        +String periodicidade
    }
    class Assinatura {
        +Long id
        +Date dataInicio
        +StatusAssinatura status
        +Date proximaEntrega
    }
    class CatalogoSemana {
        +Long id
        +Date semanaReferencia
        +DateTime dataAtualizacao
    }
    class ItemCatalogo {
        +int quantidadeDisponivel
        +boolean ativo
    }
    class CestaSemanal {
        +Long id
        +Date dataComposicao
        +StatusCesta status
    }
    class ItemCesta {
        +int quantidade
    }
    class Produto {
        +Long id
        +String nome
        +TipoProduto tipo
        +boolean disponivel
    }
    class EnderecoEntrega {
        +String cep
        +String rua
        +String numero
        +String bairro
        +String cidade
        +String complemento
    }
    class PreferenciaEntrega {
        +String diaSemana
        +String periodoEntrega
        +String observacao
    }
    class Pagamento {
        +Long id
        +double valor
        +DateTime dataProcessamento
        +StatusPagamento status
        +String codigoTransacao
    }
    class CartaoCredito {
        +String numeroMascarado
        +String nomeTitular
        +String validade
        +String bandeira
        +String tokenOperadora
    }
    class ProtocoloAssinatura {
        +String numero
        +DateTime dataGeracao
    }
    class TipoProduto {
        <<enumeration>>
        FRUTA
        LEGUME
        VERDURA
    }
    class StatusAssinatura {
        <<enumeration>>
        AGUARDANDO_APROVACAO
        APROVADA
        CANCELADA
    }
    class StatusCesta {
        <<enumeration>>
        MONTANDO
        CONFIRMADA
        APROVADA
    }
    class StatusPagamento {
        <<enumeration>>
        PENDENTE
        APROVADO
        RECUSADO
    }

    Assinante "1" -- "0..*" CodigoVerificacao : solicita
    Assinante "1" -- "0..*" Assinatura : contrata
    PlanoAssinatura "1" *-- "1..*" OpcaoPlanoAssinatura : possui
    Assinatura "1" -- "1" PlanoAssinatura : plano selecionado
    Assinatura "1" -- "1" OpcaoPlanoAssinatura : opcao escolhida
    OpcaoPlanoAssinatura "1" -- "0..*" CestaSemanal : define limites
    Assinatura "1" -- "0..*" CestaSemanal : gera cestas semanais
    CatalogoSemana "1" -- "0..*" CestaSemanal : base da escolha
    CatalogoSemana "1" *-- "1..*" ItemCatalogo : disponibiliza
    Produto "1" -- "0..*" ItemCatalogo : ofertado como
    CestaSemanal "1" *-- "1..*" ItemCesta : composta por
    ItemCesta "*" -- "1" ItemCatalogo : seleciona
    Assinatura "1" -- "1" EnderecoEntrega : entrega em
    Assinatura "1" -- "0..1" PreferenciaEntrega : define
    Assinatura "1" -- "0..*" Pagamento : gera pagamentos
    Pagamento "1" -- "1" CartaoCredito : usa
    Assinatura "1" -- "0..1" ProtocoloAssinatura : formalizada por
```

## Projeto do Caso de Uso: Assinar Serviço de Feira

Os diagramas a seguir detalham o projeto do fluxo principal do caso de uso **Assinar Serviço de Feira**, mantendo correspondência com o fluxo visual e com as telas do protótipo. O projeto considera a separação entre interface, controle do caso de uso, serviços de aplicação, entidades de domínio, repositórios e integrações externas.

### Correspondência com o caso de uso e protótipo

| Etapa do fluxo principal | Responsabilidade de projeto                                                  | Referência no protótipo |
| ------------------------ | ---------------------------------------------------------------------------- | ----------------------- |
| 1 a 4                    | Identificar o assinante por celular, enviar SMS e validar o código informado | Telas 1 a 3             |
| 5 a 6                    | Exibir planos disponíveis e registrar o plano escolhido                      | Tela 4                  |
| 7 a 15                   | Buscar produtos por categoria e montar a cesta semanal                       | Telas 5 a 7             |
| 16 a 18                  | Exibir resumo da cesta, receber endereço e confirmar compra semanal          | Tela 8                  |
| 19 a 22                  | Colocar compra em aguardando aprovação, solicitar pagamento e validar cartão | Tela 9                  |
| 23 a 25                  | Aprovar assinatura/cesta, gerar protocolo e exibir confirmação               | Tela 10                 |

## Diagrama de Sequência de Projeto

```mermaid
sequenceDiagram
    actor Assinante
    participant UI as TelaAssinaturaFeira
    participant Controller as AssinaturaController
    participant Auth as AutenticacaoSmsService
    participant Sms as SmsGateway
    participant Plano as PlanoAssinaturaService
    participant Catalogo as CatalogoSemanaService
    participant Cesta as CestaSemanalService
    participant Endereco as EnderecoEntregaService
    participant Pagamento as PagamentoService
    participant Operadora as OperadoraCartaoCreditoGateway
    participant Protocolo as ProtocoloService
    participant Repo as Repositorios

    Assinante->>UI: informa numero de celular
    UI->>Controller: iniciarCadastro(celular)
    Controller->>Auth: enviarCodigoConfirmacao(celular)
    Auth->>Sms: enviarSms(celular, codigo)
    Sms-->>Auth: smsEnviado
    Auth->>Repo: salvarCodigoVerificacao(celular, codigo)
    Controller-->>UI: solicitarCodigoSms()

    Assinante->>UI: informa codigo recebido
    UI->>Controller: confirmarCodigo(celular, codigo)
    Controller->>Auth: validarCodigo(celular, codigo)
    Auth->>Repo: consultarCodigoValido(celular, codigo)
    Repo-->>Auth: codigoValido
    Auth-->>Controller: autenticacaoConfirmada

    Controller->>Plano: listarPlanosDisponiveis()
    Plano->>Repo: buscarPlanosAtivos()
    Repo-->>Plano: planos
    Plano-->>Controller: planos
    Controller-->>UI: exibirPlanos(planos)

    Assinante->>UI: seleciona plano e confirma
    UI->>Controller: selecionarPlano(idPlano)
    Controller->>Plano: obterPlano(idPlano)
    Plano->>Repo: buscarPlano(idPlano)
    Repo-->>Plano: planoSelecionado
    Plano-->>Controller: planoSelecionado
    Controller->>Cesta: criarCestaSemanal(planoSelecionado)
    Cesta->>Repo: salvarCesta(status=MONTANDO)

    loop Para cada tipo: FRUTA, LEGUME e VERDURA
        Controller->>Catalogo: listarItensDisponiveis(tipo, planoSelecionado)
        Catalogo->>Repo: buscarProdutosDaSemana(tipo)
        Repo-->>Catalogo: produtosEQuantidades
        Catalogo-->>Controller: produtosEQuantidades
        Controller-->>UI: exibirProdutos(tipo, produtosEQuantidades)
        Assinante->>UI: escolhe itens e confirma
        UI->>Controller: confirmarItens(tipo, itensEscolhidos)
        Controller->>Cesta: adicionarItens(tipo, itensEscolhidos)
        Cesta->>Repo: salvarItensDaCesta(itensEscolhidos)
    end

    Controller-->>UI: exibirResumoCestaESolicitarEndereco()
    Assinante->>UI: informa endereco e confirma cesta
    UI->>Controller: confirmarEntrega(endereco)
    Controller->>Endereco: validarEndereco(endereco)
    Endereco-->>Controller: enderecoValidado
    Controller->>Cesta: confirmarCesta()
    Cesta->>Repo: salvarCesta(status=CONFIRMADA)
    Controller->>Repo: salvarEnderecoEntrega(enderecoValidado)
    Controller->>Repo: salvarAssinatura(status=AGUARDANDO_APROVACAO)
    Controller-->>UI: solicitarPagamento(totalAssinatura)

    Assinante->>UI: informa dados do cartao
    UI->>Controller: confirmarPagamento(dadosCartao)
    Controller->>Pagamento: processarPagamento(assinatura, dadosCartao)
    Pagamento->>Operadora: autorizarPagamento(valor, dadosCartao)
    Operadora-->>Pagamento: pagamentoAprovado(codigoTransacao)
    Pagamento->>Repo: salvarPagamento(status=APROVADO, codigoTransacao)
    Pagamento-->>Controller: pagamentoConfirmado
    Controller->>Repo: atualizarAssinatura(status=APROVADA)
    Controller->>Repo: atualizarCesta(status=APROVADA)
    Controller->>Protocolo: gerarProtocolo(assinatura)
    Protocolo-->>Controller: numeroProtocolo
    Controller->>Repo: salvarProtocolo(numeroProtocolo)
    Controller-->>UI: exibirSucesso(dadosEntrega, numeroProtocolo)
```

## Diagrama de Classes de Projeto

```mermaid
classDiagram
    class TelaAssinaturaFeira {
        <<boundary>>
        +capturarCelular()
        +capturarCodigoSms()
        +exibirPlanos(planos)
        +capturarPlano()
        +exibirProdutos(tipo, produtos)
        +capturarItens(tipo)
        +exibirResumoCesta(cesta)
        +capturarEndereco()
        +capturarDadosCartao()
        +exibirConfirmacao(protocolo, entrega)
    }

    class AssinaturaController {
        <<control>>
        +iniciarCadastro(celular)
        +confirmarCodigo(celular, codigo)
        +listarPlanos()
        +selecionarPlano(idPlano)
        +listarProdutosPorTipo(tipo)
        +confirmarItens(tipo, itens)
        +confirmarEntrega(endereco)
        +confirmarPagamento(dadosCartao)
    }

    class AutenticacaoSmsService {
        <<service>>
        +enviarCodigoConfirmacao(celular)
        +validarCodigo(celular, codigo)
    }

    class PlanoAssinaturaService {
        <<service>>
        +listarPlanosDisponiveis()
        +obterPlano(idPlano)
        +calcularTotal(plano)
    }

    class CatalogoSemanaService {
        <<service>>
        +listarItensDisponiveis(tipo, plano)
        +validarDisponibilidade(itens)
    }

    class CestaSemanalService {
        <<service>>
        +criarCestaSemanal(plano)
        +adicionarItens(tipo, itens)
        +confirmarCesta()
        +aprovarCesta()
    }

    class EnderecoEntregaService {
        <<service>>
        +validarEndereco(endereco)
        +registrarEndereco(assinante, endereco)
    }

    class PagamentoService {
        <<service>>
        +processarPagamento(assinatura, cartao)
        +registrarPagamento(retornoOperadora)
    }

    class ProtocoloService {
        <<service>>
        +gerarProtocolo(assinatura)
    }

    class SmsGateway {
        <<external>>
        +enviarSms(celular, mensagem)
    }

    class OperadoraCartaoCreditoGateway {
        <<external>>
        +autorizarPagamento(valor, cartao)
    }

    class AssinanteRepository {
        <<repository>>
        +salvar(assinante)
        +buscarPorCelular(celular)
    }

    class CodigoVerificacaoRepository {
        <<repository>>
        +salvar(codigo)
        +buscarCodigoValido(celular, codigo)
        +marcarComoUtilizado(codigo)
    }

    class PlanoAssinaturaRepository {
        <<repository>>
        +buscarPlanosAtivos()
        +buscarPorId(idPlano)
    }

    class CatalogoProdutoRepository {
        <<repository>>
        +buscarProdutosDaSemana(tipo)
    }

    class AssinaturaRepository {
        <<repository>>
        +salvar(assinatura)
        +atualizarStatus(assinatura, status)
        +salvarProtocolo(assinatura, protocolo)
    }

    class CestaSemanalRepository {
        <<repository>>
        +salvar(cesta)
        +salvarItens(cesta, itens)
        +atualizarStatus(cesta, status)
    }

    class EnderecoEntregaRepository {
        <<repository>>
        +salvar(endereco)
    }

    class PagamentoRepository {
        <<repository>>
        +salvar(pagamento)
    }

    class Assinante {
        <<entity>>
        +Long id
        +String nome
        +String celular
        +String email
        +Date dataCadastro
    }

    class CodigoVerificacao {
        <<entity>>
        +String codigo
        +DateTime dataEnvio
        +boolean utilizado
        +boolean expirado
    }

    class PlanoAssinatura {
        <<entity>>
        +Long id
        +String nome
        +double preco
        +int limiteFrutas
        +int limiteLegumes
        +int limiteVerduras
        +String descricao
    }

    class Assinatura {
        <<entity>>
        +Long id
        +String protocolo
        +Date dataInicio
        +StatusAssinatura status
        +Date proximaEntrega
        +aprovar()
        +aguardarAprovacao()
    }

    class CestaSemanal {
        <<entity>>
        +Long id
        +Date dataComposicao
        +StatusCesta status
        +adicionarItem(produto, quantidade)
        +confirmar()
        +aprovar()
    }

    class ItemCesta {
        <<entity>>
        +int quantidade
    }

    class Produto {
        <<entity>>
        +Long id
        +String nome
        +TipoProduto tipo
        +boolean disponivel
    }

    class EnderecoEntrega {
        <<value object>>
        +String cep
        +String rua
        +String numero
        +String bairro
        +String cidade
        +String complemento
    }

    class Pagamento {
        <<entity>>
        +Long id
        +double valor
        +DateTime dataProcessamento
        +StatusPagamento status
        +String codigoTransacao
    }

    class CartaoCredito {
        <<value object>>
        +String numeroMascarado
        +String nomeTitular
        +String validade
        +String tokenOperadora
    }

    class TipoProduto {
        <<enumeration>>
        FRUTA
        LEGUME
        VERDURA
    }

    class StatusAssinatura {
        <<enumeration>>
        AGUARDANDO_APROVACAO
        APROVADA
        CANCELADA
    }

    class StatusCesta {
        <<enumeration>>
        MONTANDO
        CONFIRMADA
        APROVADA
    }

    class StatusPagamento {
        <<enumeration>>
        PENDENTE
        APROVADO
        RECUSADO
    }

    TelaAssinaturaFeira --> AssinaturaController

    AssinaturaController --> AutenticacaoSmsService
    AssinaturaController --> PlanoAssinaturaService
    AssinaturaController --> CatalogoSemanaService
    AssinaturaController --> CestaSemanalService
    AssinaturaController --> EnderecoEntregaService
    AssinaturaController --> PagamentoService
    AssinaturaController --> ProtocoloService

    AutenticacaoSmsService --> SmsGateway
    AutenticacaoSmsService --> CodigoVerificacaoRepository
    AutenticacaoSmsService --> AssinanteRepository

    PlanoAssinaturaService --> PlanoAssinaturaRepository
    CatalogoSemanaService --> CatalogoProdutoRepository
    CestaSemanalService --> CestaSemanalRepository
    EnderecoEntregaService --> EnderecoEntregaRepository
    PagamentoService --> OperadoraCartaoCreditoGateway
    PagamentoService --> PagamentoRepository
    ProtocoloService --> AssinaturaRepository

    AssinanteRepository --> Assinante
    CodigoVerificacaoRepository --> CodigoVerificacao
    PlanoAssinaturaRepository --> PlanoAssinatura
    CatalogoProdutoRepository --> Produto
    AssinaturaRepository --> Assinatura
    CestaSemanalRepository --> CestaSemanal
    EnderecoEntregaRepository --> EnderecoEntrega
    PagamentoRepository --> Pagamento

    Assinante "1" --> "0..*" CodigoVerificacao : solicita
    Assinante "1" --> "0..*" Assinatura : realiza
    PlanoAssinatura "1" --> "0..*" Assinatura : define
    Assinatura "1" *-- "1" CestaSemanal : cesta da semana
    Assinatura "1" --> "1" EnderecoEntrega : entrega em
    CestaSemanal "1" *-- "1..*" ItemCesta : composta por
    ItemCesta "*" --> "1" Produto : produto
    Assinatura "1" --> "1" Pagamento : pagamento inicial
    Pagamento "1" --> "1" CartaoCredito : usa
    Produto --> TipoProduto
    Assinatura --> StatusAssinatura
    CestaSemanal --> StatusCesta
    Pagamento --> StatusPagamento
```
