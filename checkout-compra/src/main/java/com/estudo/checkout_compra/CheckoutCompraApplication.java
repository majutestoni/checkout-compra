package com.estudo.checkout_compra;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.estudo.checkout_compra.domain.sale.SaleEntity;
import com.estudo.checkout_compra.domain.sale.SaleItemEntity;
import com.estudo.checkout_compra.dtos.ProductSaleDTO;
import com.estudo.checkout_compra.dtos.SaleDTO;
import com.estudo.checkout_compra.services.sale.SaleService;

@SpringBootApplication
public class CheckoutCompraApplication implements CommandLineRunner {

	private SaleService saleService;

	public CheckoutCompraApplication(SaleService saleService) {
		this.saleService = saleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CheckoutCompraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("======== Iniciando aplicação ========");

		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("1. Iniciar nova compra");
			 System.out.println("0. Sair");
			System.out.print("Escolha: ");
			String opcao = scan.nextLine();

			switch (opcao) {
				case "1":
					SaleDTO dto = buildSale(scan);
					SaleEntity sale = saleService.newSale(dto);
					showSale(sale);
					break;
				case "0":
					System.out.println("Saindo...");
					return;
				default:
					System.out.println("Opção inválida.");
			}
		}
	}

	private void showSale(SaleEntity sale) {
		System.out.println("====== SUA COMPRA =======");
		System.out.println("Produtos: ");
		for(SaleItemEntity item : sale.getItems()) {
			System.out.println(item.getProduct().toString() + " x " + item.getQuantity());
		}

		System.out.println("Subtotal: " + sale.getValue());

		/* if (sale.getCoupon() != null) {
			System.out.println("Cupom Aplicado: " + sale.getValue());
		} */

		System.out.println("Frete: " + sale.getValueFreight());

		System.out.println("Pagamento: ");
		System.out.println("Método: " + sale.getPaymentMethod());
		// System.out.println("Parcelas: ");

		System.out.println("=========================");
	}

	private SaleDTO buildSale(Scanner scan) {

		SaleDTO dto = new SaleDTO();
		while (true) {
			System.out.println("1 - Inlcuir produto");
			System.out.println("2 - Inlcuir cupom");
			System.out.println("3 - Finalizar compra");
			System.out.println("0. Sair");
			System.out.print("Escolha: ");
			String opcao = scan.nextLine();

			switch (opcao) {
				case "1":
					ProductSaleDTO productDTO = new ProductSaleDTO();

					System.out.print("Nome: ");
					productDTO.setName(scan.nextLine());
					System.out.print("Preço: ");
					String precoStr = scan.nextLine();
					productDTO.setValue(new BigDecimal(precoStr));
					System.out.print("Código do produto: ");
					productDTO.setCodeProduct(scan.nextLine());
					System.out.print("Quantidade: ");
					productDTO.setQuantity(scan.nextLong());

					dto.addProduct(productDTO);
					break;
				case "2":
					System.out.print("Informe o código do cupom: ");
					dto.setCodeCoupon(scan.nextLine());
					break;
				case "3":
					System.out.print("Informe a forma de pagamento, sendo:\n" +
							"0 - PIX\n" +
							"1 - CREDITO\n" +
							"2 - BOLETO\n" +
							"Escolha: ");
					int formaPagamento = Integer.parseInt(scan.nextLine());
					dto.setPaymentMethod(formaPagamento);

					if (formaPagamento == 1) {
						System.out.print("Informe a quantidade de parcelas: ");
						dto.setQuantityInstallments(Integer.parseInt(scan.nextLine()));
					}

					return dto;
				case "0":
					System.out.println("Saindo...");
					return dto;
				default:
					System.out.println("Opção inválida.");
			}
		}
	}
}
