
# Checkout compra

Simulador de checkout de e-comerce usando terminal

### 🛠️ Especificações técnicas
 * IDE de programação - [IntelliJ](https://www.jetbrains.com/idea/download/?section=windows) 
 * Java 17 - [Azul zulu](https://www.azul.com/downloads/?package=jdk#zulu)
 * Execução via terminal (sem interface gráfica ou web)
* Armazenamento de dados em memória (sem banco de dados)
* [Ver esboço UML V1](uml_v1.png)


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