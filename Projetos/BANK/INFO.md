🏦 BANK - Sistema de Simulação Bancária em Java
O BANK é uma aplicação baseada em Console (CLI) desenvolvida para consolidar conceitos essenciais de Java Core, Orientação a Objetos e lógica de programação aplicada. O sistema simula operações do dia a dia de um banco, como cadastro, login, depósitos, pagamentos e consulta de saldos.

🛠️ Tecnologias e Conceitos Aplicados
1. Paradigma Orientação a Objetos (POO)
Encapsulamento: Proteção de dados sensíveis do usuário através de atributos privados e acessores (Getters/Setters).

Abstração e Entidades: Criação de classes dedicadas para representação de regras de negócio e usuários.

2. Otimização e Manipulação de Dados
Eficiência de Memória: Uso de StringBuilder para manipulação de strings de forma performática dentro de estruturas de repetição.

Validação de Inputs: Tratamento de dados de entrada com .trim() e funções de parsing (Long.parseLong(), Integer.parseInt()).

3. Concorrência e UX (User Experience)
Simulação de Processamento: Uso de Thread.sleep e tratamento de exceções para criar transições realistas entre as telas do sistema.

Interface Dinâmica: Renderização de uma barra de progresso no terminal utilizando controle de cursor (\r) e caracteres especiais.

4. Organização do Código
Arquitetura modularizada dividida em pacotes (BANK.login e BANK.operacoes) para garantir manutenibilidade e clean code.