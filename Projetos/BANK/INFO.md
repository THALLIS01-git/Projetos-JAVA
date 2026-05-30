# Documentação do Projeto: Sistema Bancário em Console (BANK CLI)

Este documento apresenta a análise técnica, arquitetural e conceitual do sistema bancário desenvolvido em Java via Interface de Linha de Comando (CLI). O projeto simula operações financeiras reais (como autenticação, depósitos e pagamentos), utilizando conceitos sólidos de Programação Orientada a Objetos (POO), manipulação de Threads e otimização de fluxos de dados.

---

## 1. Arquitetura de Software e Organização de Pacotes

O sistema adota uma divisão modular baseada em **Pacotes (Packages)** para isolar responsabilidades e garantir a manutenibilidade do código:

* **`BANK.login`**: Pacote responsável pela segurança periférica, captura de credenciais, instanciação e validação do objeto de usuário.
* **`BANK.operacoes`**: Pacote focado nas regras de negócio financeiras, processamento de pagamentos, cálculos de saldo e rotinas de interface (como feedbacks visuais de carregamento).

---

BANK (Diretório Raiz)
│
├── login/
│   └── User.java (Entidade e Estado)
│
└── operacoes/
├── CarregamentoBarra.java (Interface/UX)
├── ServicoPagamento.java  (Regra de Negócio)
└── Sistema_PAG_DEP_Fucoes.java (Core do Sistema)

---

## 2. Paradigma de Orientação a Objetos (POO)

O projeto utiliza a POO para aproximar o código do cenário do mundo real, dividindo o software entre **Entidades estruturadas** e **Classes de Serviço**:

### A. Classes, Objetos e Encapsulamento
A entidade `User` encapsula os dados privados do cliente (como nome, CPF e endereço). 
* **Privacidade dos Dados**: O acesso direto aos atributos é bloqueado. O sistema expõe o comportamento do objeto estritamente por meio de métodos acessores e modificadores públicos (*Getters* e *Setters*, como `usuario1.setNome(...)`), garantindo que o objeto controle a validação de seu próprio estado interno.

### B. Métodos Estáticos (Statics)
Classes utilitárias e de processos que não possuem estado interno próprio (como `CarregamentoBarra` e `ServicoPagamento`) utilizam métodos com o modificador `static`. 
* **Ganho Computacional**: Isso permite que o ecossistema acione comportamentos diretamente pela assinatura da classe (ex: `CarregamentoBarra.barraCarregamento()`) sem a necessidade de alocar memória na Heap criando instâncias com a palavra-chave `new`.

---

## 3. Utilização da API Nativa do Java (Java Standard API)

Para evitar dependências externas e manter o sistema leve, foram exploradas estruturas nativas do pacote `java.util` e `java.lang`:

* **`Scanner`**: Atua como o canal de entrada síncrono, interceptando os fluxos de bytes digitados pelo usuário no console e convertendo-os em tipos gerenciáveis de texto (`String`).
* **`Random`**: Utilizado como um motor de simulação pseudoaleatória para gerar dados dinâmicos em tempo de execução, como números de conta corrente e dígitos verificadores (DV).
* **`StringBuilder` (Eficiência de Memória)**: Empregado na construção de animações em laços de repetição. Ao contrário da classe `String` comum (que é imutável e cria um novo objeto na memória a cada concatenação `+`), o `StringBuilder` modifica internamente o mesmo buffer de caracteres, otimizando o uso da memória RAM.

---

## 4. Concorrência, Tempo e Controle de Fluxo

Para simular a latência e o tempo de resposta de servidores bancários reais, o sistema interage diretamente com o gerenciamento de subprocessos do Sistema Operacional:

### A. Manipulação de Threads
O método `Thread.sleep(ms)` suspende temporariamente a execução da linha de código principal (Thread main) pelo tempo determinado em milissegundos. Essa técnica de temporização cria uma cadência de quadros necessária para animações e simula o tempo de processamento de requisições.

### B. Tratamento de Exceções
Como a suspensão de uma Thread pode ser interrompida abruptamente por outros eventos do sistema operacional, o código faz o tratamento rigoroso de segurança adicionando assinaturas de erro (`throws Exception` / `InterruptedException`). Isso blinda a aplicação contra fechamentos inesperados (*crashes*).

### C. Estruturas de Decisão Encadeadas
A árvore de decisão do sistema utiliza estruturas `if / else if / else` aninhadas para garantir a integridade das operações. O sistema valida sequencialmente:
1. Se a entrada é um comando válido.
2. Se a autenticação confere.
3. Se o saldo em conta é matematicamente suficiente para liquidar a transação antes de debitar o valor.

---

## 5. Engenharia de Dados e Formatação CLI

A experiência do usuário (UX) em modo texto foi otimizada por meio de técnicas avançadas de manipulação de strings e caracteres de controle de terminal:

* **Parsing Estrito**: O sistema realiza a conversão de tipos primitivos por meio de `Long.parseLong()` e `Integer.parseInt()`. Isso transforma os dados de texto recebidos pelo terminal em dados numéricos puros para permitir validações lógicas e cálculos matemáticos (como verificação de CPFs e senhas).
* **Sanitização de Entradas (Strings)**: Métodos como `.trim()` limpam espaços vazios acidentais inseridos pelo usuário. Métodos de normalização como `.toLowerCase()` e `.equalsIgnoreCase()` anulam a sensibilidade entre letras maiúsculas e minúsculas nas tomadas de decisão.
* **Formatação Monetária**: O sistema impede falhas de arredondamento visual utilizando o `String.format("%.2f", saldo)`, padronizando a exibição de frações numéricas no formato internacional de centavos e moedas (duas casas decisivas).
* **Controle de Cursor no Terminal**: A barra de progresso utiliza o caractere especial `\r` (Carriage Return / Retorno de Carro). Esse comando instrui o console a retornar o cursor de escrita para o início da mesma linha atual (coluna 0), permitindo sobrescrever os caracteres `░` por `█` em tempo real, gerando o efeito visual de animação sem inundar o terminal com novas linhas.

---
*Documento gerado para consolidação de aprendizado em Arquitetura de Software Core Java, Estruturas de Dados e Programação Orientada a Objetos.*
