# API de Gerenciamento de Despesas
API para gerenciamento de despesas pessoais, construída com Java e Spring Boot

## 🔗 URL do Deploy
- Swagger: https://projeto-despesas.up.railway.app/swagger-ui/index.html#/

## 📦 Tecnologias
- Java 17
- Spring Boot
- Maven
- Banco de dados: PostgreSQL(Railway)
- Testes: JUnit + Mockito

## 🚀 Funcionalidades
- CRUD completo de despesas
- Filtros e buscas

## ⚙️ Sobre o banco
- O projeto já está configurado para rodar no perfil dev com application.yml e application-dev.yml.
- Banco local deve estar ativo e configurado conforme application-dev.yml.
- O deploy em produção já está disponível no Railway; não é necessário rodar produção localmente.

## 📌 Observações

- Todos os endpoints podem ser testados via Swagger.
- Ideal para integração com front-end ou Postman.

## 🛠️ Instalação / Execução local
Se alguém quiser rodar localmente

```bash
# Clonar o repositório
git clone https://github.com/tresharts/Projeto-Despesas.git

# Entrar na pasta do projeto
cd Projeto-Despesas

# Rodar a aplicação
./mvnw spring-boot:run 

