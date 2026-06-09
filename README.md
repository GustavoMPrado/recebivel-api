# Recebível API

API REST em Java com Spring Boot para controle interno de cobranças, parcelas, vencimentos e pagamentos.

Este projeto faz parte do meu portfólio backend.

A minha ideia inicial era criar uma aplicação que proporcionasse algum tipo de ganho mensurável para determinada empresa, como redução de tempo, redução de trabalho manual ou melhora no controle financeiro.

Como este projeto não foi desenvolvido dentro de uma empresa real, eu procurei simular um cenário comum em pequenos negócios: o controle de recebíveis e o acompanhamento de inadimplência.

A partir disso, desenvolvi uma API simples, mas completa o suficiente para representar um fluxo realista de recebíveis, com clientes, cobranças, parcelas, vencimentos, pagamentos, cancelamentos, autenticação, banco de dados, Docker, Swagger, deploy e testes automatizados.

O projeto foi criado com o objetivo de simular uma necessidade comum de pequenas empresas: acompanhar o que ainda precisa ser recebido, quais cobranças estão em aberto, quais parcelas venceram e quais pagamentos já foram registrados.

Não funciona como um sistema de pagamento. Não processa Pix, boleto, cartão ou checkout. O foco é controle interno: acompanhar o que ainda precisa ser recebido, quais cobranças estão em aberto, quais parcelas venceram e quais pagamentos já foram registrados.

## Projeto online

API publicada no Render:

```text
https://recebivel-api.onrender.com
```

Swagger online:

```text
https://recebivel-api.onrender.com/swagger-ui/index.html
```

Observação: por estar hospedado em plano gratuito no Render, o serviço pode demorar alguns segundos para responder após um período de inatividade.

## O que o projeto tem hoje

* API REST com Java 21 e Spring Boot
* Autenticação com login e token JWT
* Endpoints protegidos com Spring Security
* PostgreSQL com migrations usando Flyway
* Banco PostgreSQL em nuvem no Neon
* Deploy da API no Render
* Dockerfile da aplicação
* Docker Compose com API + banco
* Profiles separados para desenvolvimento e produção
* Swagger habilitado em desenvolvimento e produção
* Botão Authorize no Swagger para uso do token JWT
* Tratamento de erros em JSON
* Testes automatizados para regras principais

## Tecnologias

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* PostgreSQL
* Flyway
* Bean Validation
* Lombok
* Maven
* Docker
* Swagger/OpenAPI
* JUnit
* Mockito
* Render
* Neon

## Números do projeto

* 4 tabelas principais: cliente, cobrança, parcela e usuário
* 5 migrations com Flyway
* 20 endpoints REST
* 4 status de cobrança
* 4 status de parcela
* 2 profiles de configuração: dev e prod
* 2 containers no Docker Compose: API e PostgreSQL
* 15+ testes automatizados cobrindo serviços, autenticação e regras de negócio

## Melhorias de eficiência e manutenibilidade

* Docker Compose para subir API e PostgreSQL juntos, reduzindo a configuração manual do ambiente.
* Flyway para versionar o banco de dados e evitar criação manual de tabelas.
* DTOs de resposta para evitar retorno de objetos grandes e relacionamentos aninhados desnecessários.
* Tratamento padronizado de erros em JSON, facilitando o consumo da API.
* Profiles separados para desenvolvimento e produção, reduzindo riscos de configuração incorreta.
* Variáveis de ambiente para dados sensíveis em produção.
* Testes automatizados para validar regras importantes sem depender apenas de testes manuais.
* Swagger disponível para visualizar e testar os endpoints da API.
* Deploy em nuvem com Render e Neon.

## Funcionalidades

### Clientes

* Cadastro de cliente
* Listagem de clientes
* Busca de cliente por id
* Atualização de cliente
* Desativação de cliente

### Cobranças

* Criação de cobrança vinculada a um cliente
* Geração automática de parcelas
* Listagem de cobranças
* Busca de cobrança por id
* Filtro por status
* Filtro por cliente
* Listagem de cobranças em aberto
* Cancelamento de cobrança

### Parcelas

* Listagem de parcelas de uma cobrança
* Busca de parcela por id
* Filtro de parcelas por status
* Listagem de parcelas em aberto
* Listagem de parcelas vencidas
* Registro de pagamento
* Marcação de parcelas vencidas

### Autenticação

* Login com email e senha
* Geração de token JWT
* Proteção dos endpoints internos
* Retorno padronizado para acesso não autorizado
* Autorização via Bearer Token no Swagger

## Algumas regras implementadas

* Uma cobrança pertence a um cliente.
* Uma cobrança pode ter uma ou mais parcelas.
* As parcelas são geradas automaticamente na criação da cobrança.
* Parcela pendente pode ser paga.
* Parcela vencida também pode ser paga.
* Parcela paga não pode ser paga de novo.
* Parcela cancelada não pode ser paga.
* Ao pagar uma parcela, a data de pagamento é preenchida.
* Ao pagar uma parcela, o status da parcela muda para PAGA.
* Ao pagar parte das parcelas, a cobrança fica parcialmente paga.
* Ao pagar todas as parcelas, a cobrança fica paga.
* Ao cancelar uma cobrança, parcelas pendentes são canceladas.
* Parcelas pagas não são alteradas no cancelamento.
* Parcelas vencidas são marcadas a partir da data de vencimento.
* Cobranças em aberto são cobranças com status ABERTA ou PARCIALMENTE_PAGA.
* Parcelas em aberto são parcelas com status PENDENTE ou VENCIDA.

## Status de cobrança

* ABERTA
* PARCIALMENTE_PAGA
* PAGA
* CANCELADA

## Status de parcela

* PENDENTE
* PAGA
* VENCIDA
* CANCELADA

## Como rodar com Docker

Pré-requisitos:

* Docker Desktop instalado e aberto
* Git instalado

Clone o repositório:

```bash
git clone https://github.com/GustavoMPrado/recebivel-api.git
cd recebivel-api
```

Crie a imagem da aplicação:

```bash
docker build -t recebivel-api .
```

Suba os containers:

```bash
docker compose up -d
```

A API estará disponível em:

```text
http://localhost:8080
```

## Como rodar localmente pelo Maven

Pré-requisitos:

* Java 21
* Docker Desktop aberto
* PostgreSQL local subindo pelo Docker Compose

Suba o banco:

```bash
docker compose up -d postgres
```

No Windows PowerShell, rode a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Login

A migration cria um usuário inicial para teste:

```text
email: admin@recebivel.com
senha: Recebivel@2026
```

Endpoint:

```http
POST /usuarios/login
```

Exemplo de requisição:

```json
{
  "email": "admin@recebivel.com",
  "senha": "Recebivel@2026"
}
```

Exemplo de resposta:

```json
{
  "token": "token_jwt"
}
```

Para acessar endpoints protegidos via HTTP:

```http
Authorization: Bearer token_jwt
```

## Swagger

Swagger local:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger online:

```text
https://recebivel-api.onrender.com/swagger-ui/index.html
```

Para testar endpoints protegidos pelo Swagger:

1. Acesse o endpoint `POST /usuarios/login`.
2. Informe o email e a senha de demonstração.
3. Copie o token retornado.
4. Clique no botão `Authorize`.
5. Cole somente o token, sem aspas e sem a palavra `Bearer`.
6. Clique em `Authorize`.
7. Execute os endpoints protegidos normalmente.

Exemplo correto no Swagger:

```text
eyJhbGciOiJIUzUxMiJ9...
```

Não usar:

```text
"eyJhbGciOiJIUzUxMiJ9..."
```

Nem:

```text
Bearer eyJhbGciOiJIUzUxMiJ9...
```

## Endpoints principais

### Autenticação

```http
POST /usuarios/login
```

### Clientes

```http
GET /clientes
GET /clientes/{id}
POST /clientes
PUT /clientes/{id}
DELETE /clientes/{id}
```

### Cobranças

```http
GET /cobrancas
GET /cobrancas/{id}
GET /cobrancas/status/{status}
GET /cobrancas/cliente/{clienteId}
GET /cobrancas/em-aberto
POST /cobrancas
PUT /cobrancas/{id}/cancelamento
```

### Parcelas

```http
GET /cobrancas/{id}/parcelas
GET /cobrancas/parcelas/{id}
GET /cobrancas/parcelas/status/{status}
GET /cobrancas/parcelas/em-aberto
GET /cobrancas/parcelas/vencidas
PUT /cobrancas/parcelas/{id}/pagamento
PUT /cobrancas/parcelas/vencidas
```

## Exemplo de criação de cliente

```json
{
  "nome": "Cliente Teste",
  "documento": "12345678900",
  "email": "cliente@teste.com",
  "telefone": "32999999999"
}
```

## Exemplo de criação de cobrança

```json
{
  "descricao": "Cobrança de serviço",
  "valorTotal": 900.00,
  "dataEmissao": "2026-05-29",
  "dataVencimento": "2026-06-29",
  "quantidadeParcelas": 3,
  "clienteId": 1
}
```

## Testes

Para rodar os testes:

No Linux/macOS:

```bash
./mvnw test
```

No Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Banco de dados

O projeto usa PostgreSQL com Flyway.

As migrations criam e versionam a estrutura do banco, incluindo as tabelas de clientes, cobranças, parcelas e usuários.

Em produção, o banco utilizado está hospedado no Neon.

## Perfis

O projeto tem dois profiles principais:

* `dev`: usado para desenvolvimento local
* `prod`: usado no deploy em produção com variáveis de ambiente

O profile de produção usa variáveis como:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

Essas variáveis não ficam salvas no repositório.

## Deploy

O projeto está publicado com:

* Render para hospedagem da API
* Neon para hospedagem do PostgreSQL

API online:

```text
https://recebivel-api.onrender.com
```

Swagger online:

```text
https://recebivel-api.onrender.com/swagger-ui/index.html
```

## Sobre o projeto

Este projeto faz parte do meu portfólio backend.

A ideia foi desenvolver uma API simples, mas completa o suficiente para praticar pontos fundamentais do desenvolvimento Java com Spring Boot: criação de endpoints REST, persistência com banco relacional, migrations, autenticação, validações, tratamento de erros, Docker, Swagger, deploy e testes automatizados.

O projeto foi mantido como um monólito modular, organizado por pacotes de domínio, sem microsserviços, porque o escopo atual não exige uma arquitetura distribuída.
