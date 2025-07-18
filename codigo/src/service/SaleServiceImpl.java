package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import domain.CouponEntity;
import domain.PaymentMethod;
import domain.ProductEntity;
import domain.SaleEntity;
import domain.SaleItemEntity;

/**
 * Implementação de {@link SaleService}
 *
 * @author Majú Testoni
 */
public class SaleServiceImpl implements SaleService {

	private CouponService couponService;
	private ProductService productService;

	public SaleServiceImpl(CouponService couponService, ProductService productService) {
		this.couponService = couponService;
		this.productService = productService;
	}

	public void newSale(Scanner scan) {
		requstData(scan);
	}

	private void requstData(Scanner scan) {
		SaleEntity saleEntity = new SaleEntity();

		System.out.println("1 - Adicionar item");
		System.out.println("2 - Adicionar cupom");
		System.out.println("3 - Finalizar compra");
		System.out.println("0 - Sair");
		System.out.print("Escolha: ");
		int opcao = scan.nextInt();

		while (opcao != 0 || opcao != 3) {
			switch (opcao) {
				case 1:
					newItemProduct(saleEntity, scan);
					break;
				case 2:
					setCoupon(saleEntity, scan);
					break;
				case 3:
					setPayment(saleEntity, scan);

					if (!validSale(saleEntity)) {
						System.out.println("Por favor, corrija os dados da compra antes de finalizar.");
						break;
					}

					showData(saleEntity);
					return;
				default:
					System.out.println("Opção inválida");
					break;
			}

			System.out.println("1 - Adicionar item");
			System.out.println("2 - Adicionar cupom");
			System.out.println("3 - Finalizar compra");
			System.out.println("0 - Sair");
			System.out.print("Escolha: ");
			opcao = scan.nextInt();
		}


	}

	private boolean validSale(SaleEntity saleEntity) {
		if (saleEntity.getItems() == null || saleEntity.getItems().isEmpty()) {
			System.out.println("Erro: Nenhum item adicionado à compra.");
			return false;
		}

		if (saleEntity.getPaymentMethod() == null) {
			System.out.println("Erro: Nenhuma forma de pagamento foi selecionada.");
			return false;
		}

		if (saleEntity.getPaymentMethod() == PaymentMethod.CREDITO &&
				(saleEntity.getQuantityInstallments() < 1 || saleEntity.getQuantityInstallments() > 12)) {
			System.out.println("Erro: Quantidade de parcelas inválida para pagamento com crédito.");
			return false;
		}

		return true;
	}


	private void newItemProduct(SaleEntity saleEntity, Scanner scan) {
		ProductEntity product = productService.newProduct(scan);
		SaleItemEntity item = new SaleItemEntity();
		item.setProduct(product);

		int quantidade = 0;
		while (quantidade < 1) {
			System.out.print("Quantidade: ");
			if (scan.hasNextInt()) {
				quantidade = scan.nextInt();
				if (quantidade < 1) {
					System.out.println("Quantidade deve ser maior que zero. Tente novamente.");
				}
			} else {
				System.out.println("Entrada inválida. Digite um número inteiro.");
				scan.next();
			}
		}

		item.setQuantity(quantidade);
		saleEntity.addItem(item);
	}


	private void setPayment(SaleEntity saleEntity, Scanner scan) {
		scan.nextLine();

		int paymentType = -1;

		while (paymentType < 0 || paymentType > 2) {
			System.out.print("Informe a forma de pagamento, sendo:\n" +
					"0 - PIX\n" +
					"1 - CREDITO\n" +
					"2 - BOLETO\n" +
					"Escolha: ");
			if (scan.hasNextInt()) {
				paymentType = scan.nextInt();
				if (paymentType < 0 || paymentType > 2) {
					System.out.println("Opção inválida. Tente novamente.");
				}
			} else {
				System.out.println("Entrada inválida. Informe um número.");
				scan.next();
			}
		}

		saleEntity.setPaymentMethod(PaymentMethod.fromIndex(paymentType));

		if (paymentType == 1) {
			int quantity = 0;

			while (quantity < 1 || quantity > 12) {
				System.out.print("Informe a quantidade de parcelas (1 a 12): ");
				if (scan.hasNextInt()) {
					quantity = scan.nextInt();
					if (quantity < 1 || quantity > 12) {
						System.out.println("Quantidade inválida. Deve estar entre 1 e 12.");
					}
				} else {
					System.out.println("Entrada inválida. Informe um número inteiro.");
					scan.next();
				}
			}

			saleEntity.setQuantityInstallments(quantity);
		}
	}


	private void showData(SaleEntity saleEntity) {
		System.out.println("------ SUA COMPRA --------  ");

		System.out.println("Produtos:");
		for (SaleItemEntity item: saleEntity.getItems()) {
			System.out.println(item.getProduct().toString() + " x " + item.getQuantity());
		}
		
		BigDecimal valueProducts = getValuePRoducts(saleEntity);
		System.out.println("Subtotal: R$" + valueProducts);

		CouponEntity couponEntity = saleEntity.getCoupon();
		BigDecimal discountCupom = couponService.applyCoupon(couponEntity, valueProducts);

		BigDecimal valueFreight = getValueFreight(couponEntity, valueProducts);
		BigDecimal totalFinal = valueProducts.subtract(discountCupom).add(valueFreight);

		System.out.println("Total com Descontos: R$" + totalFinal);

		System.out.println("Pagamento: ");
		BigDecimal totalValue = getValuePayment(totalFinal, saleEntity);

		System.out.println("Total final: R$" + totalValue);
		System.out.println("Status: Pedido Finalizado");
		System.out.println("--------------------------");

	}

	private BigDecimal getValuePayment(BigDecimal totalFinal, SaleEntity saleEntity) {
		System.out.println(" - Método: " + saleEntity.getPaymentMethod());
		LocalDateTime dateCurrente = LocalDateTime.now();
		switch (saleEntity.getPaymentMethod()) {
			case PIX:
				BigDecimal discount = totalFinal.multiply(new BigDecimal("0.10"));
				System.out.println(" - Desconto de R$: " + discount + " (10%)");
				LocalDateTime timePix = dateCurrente.plusMinutes(30);
				System.out.println(" - Pagamento até as " + timePix.getHour() + ":" + timePix.getMinute() + " horas");
				return totalFinal.subtract(discount);
			case BOLETO:
				BigDecimal discountB = totalFinal.multiply(new BigDecimal("0.05"));
				System.out.println(" - Desconto de R$: " + discountB + " (5%)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formatDate = dateCurrente.plusDays(3).format(formatter);
				System.out.println(" - Pagamento até dia: " + formatDate);
				return totalFinal.subtract(discountB);
			case CREDITO:
				int installments = saleEntity.getQuantityInstallments();
				if (installments <= 3) {
					BigDecimal installmentsNoFess = totalFinal.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);
					System.out.printf(" - Parcelas: %dx de R$ %.2f (sem juros)%n", installments, installmentsNoFess);
					return totalFinal;
				} else {
					double taxa = 1 + 0.02;
					double totalComJuros = totalFinal.doubleValue() * Math.pow(taxa, installments);
					totalFinal = BigDecimal.valueOf(totalComJuros).setScale(2, RoundingMode.HALF_UP);

					BigDecimal valueInstallment = totalFinal.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);
					System.out.printf(" - Parcelas: %dx de R$ %.2f (com juros)%n", installments, valueInstallment);

					return totalFinal;
				}

			default:
				return totalFinal;
		}
	}

	private BigDecimal getValueFreight(CouponEntity couponEntity, BigDecimal valueProducts) {
		if (couponEntity != null && couponService.isFreightFree(couponEntity)) {
			System.out.println("Frete: R$ 0,00 (CUPOM)");
			return BigDecimal.ZERO;
		} else if (valueProducts.compareTo(BigDecimal.valueOf(250)) > 0) {
			System.out.println("Frete: R$ 0,00 (Frete grátis automático)");
			return BigDecimal.ZERO;
		}

		System.out.println("Frete: R$ 20,00");
		return new BigDecimal(20);
	}

	private BigDecimal getValuePRoducts(SaleEntity sale) {
		return sale.getItems().stream()
				.map(item -> item.getProduct().getValue().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void setCoupon(SaleEntity saleEntity, Scanner scan) {
		if (saleEntity.getCoupon() != null) {
			int opcao = -1;
			while (opcao != 1 && opcao != 2) {
				System.out.println("Você já possui um cupom... deseja alterar?");
				System.out.print("1 - Sim\n2 - Não\nEscolha: ");

				if (scan.hasNextInt()) {
					opcao = scan.nextInt();
					if (opcao != 1 && opcao != 2) {
						System.out.println("Opção inválida. Escolha 1 ou 2.");
					}
				} else {
					System.out.println("Entrada inválida. Digite um número.");
					scan.next();
				}
			}

			if (opcao == 1) {
				couponService.setCouponSale(saleEntity, scan);
			}
		} else {
			couponService.setCouponSale(saleEntity, scan);
		}
	}
}
