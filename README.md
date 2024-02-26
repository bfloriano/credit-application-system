# API Rest para um Sistema de Analise de Solicitação de Crédito

Essa aplicação foi um desafio de projeto do curso **desenvolvimento backend com kotlin**, oferecido pela [dio](https://www.dio.me/), ministrado pela professora [Cami](https://github.com/cami-la).

---

![Java](https://img.shields.io/badge/Java-v17-blue.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-v1.7.22-purple.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-v3.0.4-brightgreen.svg)
![Gradle](https://img.shields.io/badge/Gradle-v7.6-lightgreen.svg)
![H2](https://img.shields.io/badge/H2-v2.1.214-darkblue.svg)
![Flyway](https://img.shields.io/badge/Flyway-v9.5.1-red.svg)

---


## Descrição do Projeto

Uma empresa de empréstimo precisa criar um sistema de análise de solicitação de crédito. Sua tarefa será criar uma API REST SPRING BOOT E KOTLIN para a empresa fornecer aos seus clientes as seguintes funcionalidades:

- ### Cliente (Customer):
    - **Cadastrar:**

      a. **Request**: firstName, lastName, cpf, income, email, password, zipCode e street

      b. **Response**: String

    - **Editar cadastro:**
      
      a. **Request**: id, firstName, lastName, income, zipCode, street
      
      b. **Response**: firstName, lastName, income, cpf, email, income, zipCode, street

    - **Visualizar perfil:**
      
      a. **Request**: id
      
      b. **Response**: firstName, lastName, income, cpf, email, income, zipCode, street

    - **Deletar cadastro:**
      
      a. **Request**: id
      
      b. **Response**: sem retorno

- ### Solicitação de Empréstimo (Credit):
    - **Cadastrar:**
      
      a. **Request**: creditValue, dayFirstOfInstallment, numberOfInstallments e customerId
      
      b. **Response**: String
      
    - **Listar todas as solicitações de emprestimo de um cliente:**
      
      a. **Request**: customerId
      
      b. **Response**: creditCode, creditValue, numberOfInstallment
      
    - **Visualizar um emprestimo:**
      
      a. **Request**: customerId e creditCode
      
      b. **Response**: creditCode, creditValue, numberOfInstallment, status, emailCustomer e incomeCustomer

      ![Diagrama UML Simplificado de uma API para Sistema de Avaliação de Crédito](https://i.imgur.com/7phya16.png)
      Diagrama UML Simplificado de uma API para Sistema de Avaliação de Crédito


      ![Arquitetura em 3 camadas Projeto Spring Boot](https://i.imgur.com/1Ea5PH3.png)
      Arquitetura em 3 camadas Projeto Spring Boot


