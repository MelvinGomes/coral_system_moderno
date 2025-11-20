# Sistema de Gestão de Coral

![Status do Projeto](https://img.shields.io/badge/status-concluído-brightgreen)

Aplicação web desenvolvida em Java com Spring Boot para o gerenciamento completo de um coral, permitindo o cadastro de coristas, músicos, agenda de eventos e controle de presenças. O projeto utiliza uma arquitetura RESTful no backend e uma interface de página única (SPA) dinâmica no frontend.

## 📋 Índice

- [Descrição](#-descrição)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Estrutura da API](#-estrutura-da-api)
- [Equipe](#-equipe)

## 📝 Descrição

O objetivo deste sistema é centralizar e simplificar a administração de um coral. Através de uma interface web intuitiva, é possível realizar o cadastro, a edição e a exclusão de participantes e eventos, além de gerenciar a lista de presença para cada apresentação, facilitando a organização do grupo.

## ✨ Funcionalidades

O sistema possui funcionalidades de CRUD (Create, Read, Update, Delete) completas para as seguintes áreas:

*   **Gerenciamento de Coristas:**
    *   Listar todos os coristas cadastrados.
    *   Adicionar um novo corista (nome, tipo de voz, status).
    *   Editar as informações de um corista existente.
    *   Excluir um corista.

*   **Gerenciamento de Músicos:**
    *   Listar todos os músicos.
    *   Adicionar um novo músico (nome, instrumento, status).
    *   Editar os dados de um músico.
    *   Excluir um músico.

*   **Gerenciamento da Agenda:**
    *   Listar todos os eventos e apresentações.
    *   Adicionar um novo evento (data, local, descrição).
    *   Editar um evento cadastrado.
    *   Excluir um evento.

*   **Controle de Presenças:**
    *   Para cada evento na agenda, é possível visualizar uma lista de todos os coristas e músicos.
    *   Marcar e salvar a presença de cada participante no evento.

## 🚀 Tecnologias Utilizadas

#### **Backend**
*   **Java 11+**
*   **Spring Boot:** Framework principal para a construção da aplicação.
*   **Spring Web:** Para a criação dos controllers e da API RESTful.
*   **Spring Security:** Para configuração de permissões e segurança da API (CORS).
*   **H2 Database:** Banco de dados em memória para desenvolvimento e testes.
*   **Maven:** Gerenciador de dependências do projeto.

#### **Frontend**
*   **HTML5**
*   **CSS3** (básico)
*   **JavaScript (ES6+):** Utilizado para criar a interface dinâmica (Single Page Application), consumir a API com `fetch` e renderizar o conteúdo.

## ▶️ Como Executar o Projeto

#### **Pré-requisitos**
*   **JDK 11** ou superior instalado.
*   **Apache Maven** instalado.

#### **Passos**
1.  Clone o repositório:
    ```bash
    git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
    ```
2.  Navegue até o diretório raiz do projeto:
    ```bash
    cd SEU_REPOSITORIO
    ```
3.  Execute a aplicação usando o Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  Após a inicialização, acesse a aplicação no seu navegador:
    [http://localhost:8080](http://localhost:8080)

## 📡 Estrutura da API

A aplicação expõe os seguintes endpoints RESTful:

#### **Coristas (`/api/coristas`)**
- `GET /`: Retorna a lista de todos os coristas.
- `GET /{id}`: Retorna os dados de um corista específico.
- `POST /`: Adiciona um novo corista.
- `PUT /{id}`: Atualiza os dados de um corista existente.
- `DELETE /{id}`: Exclui um corista.

#### **Músicos (`/api/musicos`)**
- `GET /`: Retorna a lista de todos os músicos.
- `GET /{id}`: Retorna os dados de um músico específico.
- `POST /`: Adiciona um novo músico.
- `PUT /{id}`: Atualiza os dados de um músico existente.
- `DELETE /{id}`: Exclui um músico.

#### **Agenda (`/api/agenda`)**
- `GET /`: Retorna a lista de todos os eventos.
- `GET /{id}`: Retorna os dados de um evento específico.
- `POST /`: Adiciona um novo evento.
- `PUT /{id}`: Atualiza os dados de um evento existente.
- `DELETE /{id}`: Exclui um evento.

#### **Presenças (`/api/agenda/{id}/presencas`)**
- `GET`: Retorna a lista de presenças de um evento específico.
- `POST`: Salva a lista de presenças de um evento específico.

## 👥 Equipe

Este projeto foi desenvolvido com a colaboração de:

*   [Melvin Gomes](https://github.com/MelvinGomes)
*   [Nicole](https://github.com/Elociny/)
*   [Vinicius Avarelo](https://github.com/ViniAvarelo)
*   [Ana Paula](https://github.com/AnaPaula2024)
*   [Cintia Carvalho](https://github.com/cintiacarvv)
*   [Tayná Araújo](https://github.com/taynaaraujobispo)
*   [Miguel Luiz](https://github.com/limmuz)