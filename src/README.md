# Codigo-fonte

Aplicacao Java de console para o caso de uso **Assinar Servico de Feira**.

## Compilar

```bash
javac -d out $(find src -name "*.java")
```

## Executar

```bash
java -cp out br.com.feiraassinatura.Main
```

Os arquivos de persistencia CSV/TXT sao criados automaticamente em `src/data` na primeira execucao.
O codigo SMS simulado exibido no console e `123456`.
