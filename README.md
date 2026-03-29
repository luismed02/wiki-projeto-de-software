# Projeto de Software - Assinatura de Feira

## Visão geral
Cada vez mais os serviços de compra por assinatura têm se popularizado. Neste projeto, considera-se um serviço de assinatura semanal de frutas, legumes e verduras, inspirado em soluções como Feira na Box e Frutas em Casa.

O cenário de uso principal é Assinar Serviço de Feira, no qual o assinante seleciona plano, monta a cesta semanal, informa endereço de entrega e realiza o pagamento com validação junto à operadora de cartão de crédito.

- Ator principal: Assinante
- Ator secundário: Operadora de Cartão de Crédito
- Objetivo: realizar a assinatura de um plano de entrega semanal de produtos comercializados em feiras livres para entrega no endereço definido pelo assinante
- Pré-condições: planos de assinatura exibidos e catálogo de produtos da semana atualizado
- Pós-condição: informações do assinante validadas e armazenadas, plano selecionado armazenado, endereço validado e armazenado, preferências de entrega definidas e cesta da semana confirmada com pagamento aprovado

## Navegação rápida
- Fluxo visual do caso de uso: [wiki/user_flow.png](wiki/user_flow.png)
- Versão vetorial do fluxo: [wiki/user_flow.svg](wiki/user_flow.svg)
- Capturas das telas do protótipo: [wiki/telas](wiki/telas)

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
        +double preco
        +int limiteItens
        +String descricao
    }
    class Assinatura {
        +Long id
        +String protocolo
        +Date dataInicio
        +StatusAssinatura status
        +Date proximaEntrega
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
    class Pagamento {
        +Long id
        +double valor
        +DateTime dataProcessamento
        +StatusPagamento status
        +String codigoTransacao
    }
    class CartaoCredito {
        +String numero
        +String nomeTitular
        +String validade
        +String cvv
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

    Assinante "1" --> "0..*" CodigoVerificacao : solicita
    Assinante "1" --> "0..*" Assinatura : realiza
    PlanoAssinatura "1" --> "0..*" Assinatura : define
    Assinatura "1" *-- "1..*" CestaSemanal : contém
    Assinatura "1" --> "1" EnderecoEntrega : possui
    CestaSemanal "1" *-- "1..*" ItemCesta : composta por
    ItemCesta "*" --> "1" Produto : referência
    Assinatura "1" --> "0..*" Pagamento : gera
    Pagamento "1" --> "1" CartaoCredito : usa
    Produto --> TipoProduto : tipo
    Assinatura --> StatusAssinatura : status
    CestaSemanal --> StatusCesta : status
    Pagamento --> StatusPagamento : status
```
