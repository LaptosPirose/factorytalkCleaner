# Arquitetura do FactoryTalkCleaner

## Visão Geral

O FactoryTalkCleaner é uma ferramenta desenvolvida para realizar o saneamento, normalização e análise de dados provenientes de sistemas industriais baseados em FactoryTalk Alarm & Events.

Seu objetivo principal é reduzir ruídos em bases históricas de alarmes, removendo eventos redundantes e produzindo dados mais consistentes para análises operacionais, indicadores de desempenho e sistemas de monitoramento.

No futuro, o FactoryTalkCleaner fará parte da suíte Watch Tower, atuando como o módulo responsável pela preparação e qualidade dos dados.

---

## Objetivos da Arquitetura

A arquitetura do projeto foi definida com os seguintes princípios:

* Separação clara de responsabilidades.
* Facilidade de manutenção.
* Facilidade de testes.
* Baixo acoplamento entre módulos.
* Possibilidade de expansão futura.
* Integração simples com outros componentes da suíte Watch Tower.

---

## Fluxo Geral de Processamento

```text
Fonte de Dados
      │
      ▼
Leitura dos Registros
      │
      ▼
Normalização
      │
      ▼
Regras de Limpeza
      │
      ▼
Geração de Estatísticas
      │
      ▼
Exportação dos Resultados
```

---

## Estrutura Lógica

### Camada de Entrada

Responsável por obter os dados de origem.

Possíveis fontes:

* Arquivos CSV
* Bancos de dados relacionais
* APIs futuras
* Exportações FactoryTalk

Responsabilidades:

* Leitura dos registros
* Validação básica
* Conversão para objetos internos

---

### Camada de Normalização

Responsável por padronizar os dados recebidos.

Exemplos:

* Remoção de espaços desnecessários
* Padronização de textos
* Correção de formatos
* Conversão de tipos

Objetivo:

Garantir que as regras de limpeza operem sobre dados consistentes.

---

### Camada de Limpeza

Núcleo do sistema.

Responsável por aplicar as regras de saneamento.

Exemplos:

* Remoção de eventos redundantes
* Consolidação de registros equivalentes
* Filtragem de ruídos operacionais
* Exclusão de eventos irrelevantes

As regras devem permanecer independentes e facilmente extensíveis.

---

### Camada de Estatísticas

Responsável por gerar indicadores sobre os dados processados.

Exemplos:

* Quantidade de registros removidos
* Quantidade de registros mantidos
* Percentual de redução da base
* Alarmes mais frequentes
* Eventos mais recorrentes

---

### Camada de Exportação

Responsável pela geração dos resultados finais.

Possíveis formatos:

* CSV
* Excel
* JSON
* Relatórios PDF (futuro)

---

## Estrutura de Pacotes

```text
src/main/java
└── br/com/watchtower/factorytalkcleaner
    ├── config
    ├── model
    ├── repository
    ├── service
    ├── rules
    ├── statistics
    ├── export
    └── util
```

### Descrição dos Pacotes

#### model

Entidades utilizadas pelo sistema.

#### repository

Responsável pelo acesso aos dados.

#### service

Orquestra o fluxo de processamento.

#### rules

Contém as regras de limpeza.

#### statistics

Geração de métricas e indicadores.

#### export

Exportação dos resultados.

#### util

Funções auxiliares compartilhadas.

---

## Princípios de Desenvolvimento

As seguintes diretrizes devem ser observadas durante a evolução do projeto:

* Priorizar código legível.
* Evitar duplicação de lógica.
* Criar componentes reutilizáveis.
* Isolar regras de negócio.
* Manter cobertura de testes sempre que possível.
* Documentar decisões arquiteturais relevantes.

---

## Evolução Futura

Planejamentos já identificados:

* Interface gráfica.
* Containerização com Docker.
* Processamento de grandes volumes de dados.
* Integração com bancos de dados industriais.
* Relatórios avançados.
* API REST.
* Integração com a suíte Watch Tower.

---

## Integração com Watch Tower

No ecossistema Watch Tower, o FactoryTalkCleaner terá a função de preparação e qualificação dos dados.

Fluxo previsto:

```text
FactoryTalk Database
        │
        ▼
FactoryTalkCleaner
        │
        ▼
Base Tratada
        │
        ▼
Watch Tower
        │
        ▼
Dashboards, Indicadores e Monitoramento
```

O objetivo é garantir que os módulos analíticos do Watch Tower operem sobre dados previamente saneados, aumentando a confiabilidade dos resultados.

---

## Status Atual

Projeto em desenvolvimento ativo.

A arquitetura descrita neste documento representa a direção planejada para evolução do sistema e poderá ser refinada conforme novas necessidades forem identificadas.
