
# Checkout compra

Simulador de checkout de e-comerce usando terminal

### 🛠️ Especificações técnicas
 * IDE de programação - [IntelliJ](https://www.jetbrains.com/idea/download/?section=windows) 
 * Java 17 - [Azul zulu](https://www.azul.com/downloads/?package=jdk#zulu)
 * Execução via terminal (sem interface gráfica ou web)
* Armazenamento de dados em memória (sem banco de dados)
* [Ver esboço UML V1](uml_v1.png)
* Teste automatizado - [JUnit 5](https://junit.org/)
* Gerenciador de dependência -  [Maven](https://maven.apache.org/)

### ▶️ Execução do programa
1. Clonar o repositorio 
```bash
  git clone install https://github.com/majutestoni/checkout-compra.git 
```

2. No **Intellij** (ou sua IDE de preferência), abra a pasta `codigo` (nela conterá o código java)

3. Abrir o arquivo `Main.java` e dar `run`

### 📋 Usando o programa
O **sistema é interativo via terminal**, com menus guiados e opções numéricas.

Se um valor inválido for informado (ex: letra onde era número), o sistema irá exibir:
> Valor fora do intervalo permitido.

E solicitará nova entrada.

### 📐 Explicando um pouco do código

Visando uma melhor organização, toda a parte de coleta de dados, tratamentos e processamento ficam nos serviços, sendo a `Main` somente para chamar o `SaleService` (vulgo serviço principal) 

| Entitdade | Representa | Serviço | Implementação |
| ------ | ------ | ------ | ------ |
| `SaleEntity` | Compra/Venda | `SaleService` | `SaleServiceImpl` |
| `ProductEntity` | Produtor | `ProductService` | ProductServiceImpl |
| `CouponEntity` | Cupom | `CouponService` | `CouponServiceImpl` |
| `SaleItemEntity` | Item da compra | - | - |

### ✅ Testes
Com o objetivo de garantir segurança e confiabilidade no código, foram implementados testes unitários utilizando o framework `JUnit 5`.

Para facilitar a execução dos testes, o projeto adota o `Maven` como gerenciador de dependências.

**Como executar os testes**

1. Com o projeto aberto na sua IDE:
2. Expanda a pasta `src/test/java`;
3. Clique com o botão direito sobre a pasta de testes ou sobre qualquer classe de teste específica;
4. Selecione a opção `Run`