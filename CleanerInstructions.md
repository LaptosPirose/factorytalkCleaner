# FactoryTalk Cleaner - Guia de Execução e Implantação

Este repositório contém o **FactoryTalk Cleaner**, um serviço autônomo baseado em Spring Boot projetado para realizar a governança, higienização e manutenção automatizada do banco de dados de alarmes industriais (SQL Server) gerados pelo FactoryTalk, operando de maneira segura e otimizada para evitar travamentos (`table locks`) no ambiente fabril.

---

## 🚀 Como Executar o Sistema

Sempre execute os comandos a partir da **pasta raiz** do projeto (onde o arquivo `pom.xml` está localizado).

### Passo 1: Compilar o Projeto e Gerar o Executável (`.jar`)
Utilize o Maven Wrapper incluído no projeto para limpar compilações antigas, baixar dependências necessárias e empacotar a aplicação atualizada:

mvnw clean package

### Passo 2: Entrar na Pasta de Destino
Navegue até a pasta onde o binário compilado foi gerado:

DOS
cd target

### Passo 3: Iniciar o Serviço Autônomo
Execute o arquivo .jar utilizando a Máquina Virtual Java (JVM):

DOS
java -jar factorytalkCleaner-0.0.1-SNAPSHOT.jar


⚙️ Detalhes de Funcionamento
Porta do Serviço: A aplicação inicializa um servidor Tomcat embutido e expõe o serviço na porta 8085 (conforme configurado no application.properties).

Rotina Automática (@Scheduled): O sistema opera em segundo plano e possui um agendamento estratégico programado via expressão Cron para disparar a higienização automaticamente às 03:00 da manhã.

Segurança Operacional: A janela das 3h garante uma margem de segurança confortável após o encerramento do turno de produção (que vai até as 02:02), mitigando qualquer risco de concorrência com a planta ativa e protegendo a integridade do banco SQL Server por meio de exclusões em lote controladas com WITH (ROWLOCK).