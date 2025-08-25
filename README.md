# Desafio Técnico - Fullstack (Java + Angular)

Este projeto é composto por **backend em Java (Spring Boot)** e **frontend em Angular**, utilizando **Oracle XE** como banco de dados.  
O ambiente pode ser executado via **Docker Compose**, que orquestra os três serviços: banco de dados, backend e frontend.  

---

## 🚀 Estrutura do Projeto

- **Banco de Dados**: Oracle XE (imagem `gvenzl/oracle-xe:21-slim`)
- **Backend**: Java 17 + Spring Boot  
  Diretório: `./Desafio-Tecnico`  
- **Frontend**: Angular  
  Diretório: `./desafio-portifolio`  

---

## ⚙️ Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/)  
- [Docker Compose](https://docs.docker.com/compose/)  
- [Java 17+](https://adoptium.net/)  
- [Gradle](https://gradle.org/install/) ou wrapper (`./gradlew`)  
- [Node.js + Angular CLI](https://angular.io/cli)  

---

## 📦 Build do Projeto

Antes de subir os containers, é necessário **gerar os artefatos do backend e frontend**:

### 🔹 Backend (Java - Spring Boot)
No diretório `./Desafio-Tecnico` execute:

```bash
./gradlew clean build -x test

### 🔹 Frontend (Angular)

No diretório `./desafio-portifolio` execute:

```bash
npm install
ng build --configuration production


## ▶️ Executando com Docker Compose

Após gerar os artefatos, volte para a raiz do projeto e execute:

```bash
docker compose up -d --build


Isso irá subir os seguintes serviços:

- **Oracle XE** → porta `1521`  
- **Backend (Spring Boot)** → porta `8080`  
- **Frontend (Angular)** → porta `4200`  

---

## 🌐 Acessos

- **Frontend (Angular)**: [http://localhost:4200](http://localhost:4200)  
- **Backend (API Spring Boot)**: [http://localhost:8080](http://localhost:8080)  
- **Banco de Dados (Oracle XE)**: `localhost:1521/XEPDB1`  

---

## 🔑 Variáveis de Ambiente

As variáveis do banco de dados estão definidas no `docker-compose.yml`.  
Exemplo de `.env`:

```env
DB_USER=meu_usuario
DB_PASSWORD=minha_senha

