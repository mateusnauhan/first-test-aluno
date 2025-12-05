# First Test Aluno

Projeto acadêmico em **Java** utilizando **Maven**, **JUnit**, **Mockito** e **JaCoCo**, desenvolvido seguindo a abordagem **TDD (Test Driven Development)**.  
O projeto evoluiu para uma arquitetura em camadas, contemplando **Entity**, **Repository**, **Service** e **Controller**, todas com seus respectivos testes unitários.

---

## 🎯 Funcionalidades do Projeto

* Estrutura modular em camadas:  
  - **Entity** (representação de dados)  
  - **Repository** (simulação de persistência)  
  - **Service** (regras de negócio)  
  - **Controller** (ponto de acesso da aplicação)

* Testes unitários para cada camada:
  - Testes de entidade
  - Testes de repositório
  - Testes de service com **Mockito**
  - Testes de controller

* Relatórios automáticos de:
  - **Execução de testes** (Surefire)
  - **Cobertura de código** (JaCoCo)

* Prática completa de **TDD**:
  - Criar testes → Implementar código → Refatorar mantendo cobertura.

---

## 🛠 Tecnologias Utilizadas

* **Java 17+**
* **Maven**
* **JUnit 5**
* **Mockito**
* **JaCoCo**
* **Surefire Plugin**

---

##  Como Executar

### 1️⃣ Clone o repositório:

```sh
git clone https://github.com/joaorafael1504/first-test-aluno.git
cd first-test-aluno
```

### 2️⃣ Compile e rode os testes:

```sh
mvn clean test
```

### 3️⃣ Gerar relatório de cobertura:

```sh
mvn jacoco:report
```

O relatório estará disponível em:
 `target/site/jacoco/index.html`

### 4️⃣ Relatório de testes JUnit:

Os relatórios de execução estarão disponíveis em:
 `target/surefire-reports`

---

## 📂 Estrutura do Projeto

```plaintext
first-test-aluno/
 ├── src/
 │   ├── main/java/com/aluno/
 │   │   ├── entity/        # Entidades do sistema
 │   │   ├── repository/    # Repositórios simulando persistência
 │   │   ├── service/       # Regras de negócio
 │   │   └── controller/    # Classe responsável pelo fluxo principal
 │   └── test/java/com/aluno/
 │       ├── entity/        # Testes das entidades
 │       ├── repository/    # Testes do repositório
 │       ├── service/       # Testes usando Mockito
 │       └── controller/    # Testes do controller
 ├── target/                 # Artefatos gerados
 └── pom.xml                 # Configurações Maven
```

---

## 🧪 Metodologia TDD

* **Passo 1:** Criar o teste que representa a funcionalidade desejada.
* **Passo 2:** Verificar o teste falhar (Red).
* **Passo 3:** Implementar o código mínimo necessário para fazê-lo passar (Green).
* **Passo 4:** Refatorar mantendo todos os testes passando (Refactor).

Relatórios de cobertura (**JaCoCo**) e execução de testes (**JUnit**) foram utilizados para validar cada etapa.

---

## 📘 User Story

**Como** um aluno assinante básico
**Quero** liberar mais 3 cursos ao concluir um curso com média acima de 7,0
**Para** continuar ampliando meus estudos e ter acesso contínuo a novos conteúdos.

---

## 🧪 BDDs

### ✔️ BDD 1 – Cenário de Sucesso

**Dado** que sou um aluno assinante básico
**E** finalizei um curso com média 8
**Quando** o sistema validar minha nota
**Então** devo ter acesso liberado automaticamente a mais 3 cursos.

---

### ❌ BDD 2 – Cenário de Fracasso

**Dado** que sou um aluno assinante básico
**E** finalizei um curso com média 6,5
**Quando** o sistema validar minha nota
**Então** não devo receber a liberação de novos cursos.

---

### 🔄 BDD 3 – Cenário de Upgrade para Premium

**Dado** que sou um aluno assinante básico
**E** concluí mais de 12 cursos com média acima de 7,0
**Quando** o sistema verificar que concluí mais de 12 cursos
**Então** meu plano deve mudar para Premium.

---

### 🛑 BDD 4 – Cenário de Prevenção de Benefício Duplicado

**Dado** que o aluno concluiu um curso com média 8,5
**E** já recebeu 3 cursos adicionais por esse resultado
**Quando** o sistema processar novamente a conclusão do mesmo curso
**Então** o saldo de cursos do aluno não deve aumentar.

---

## 👨‍💻 Autores

Desenvolvido por:

* João Rafael
* Milton Penha
* Mateus Nauhan
* Felipe Rusig

---

## 📜 Licença

Este projeto está sob a licença MIT.
Sinta-se livre para contribuir! 
