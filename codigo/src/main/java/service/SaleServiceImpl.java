package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
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

	private Map<Long, SaleEntity> sales = new HashMap<>();
	private int geraId = 0; // auxilia para gerar ids

	private CouponService couponService;
	private ProductService productService;

	public SaleServiceImpl(CouponService couponService, ProductService productService) {
		this.couponService = couponService;
		this.productService = productService;
	}

	@Override
	public void newSale(Scanner scan) {
		requstData(scan);
	}

	@Override
	public void showData(Scanner scan) {
		int opcao = 1;
		while (opcao != 0) {
			System.out.print("Informe o ID da compra: ");
			long idConsulta = scan.nextLong();
			SaleEntity sale = sales.get(idConsulta);

			if (sale == null) {
				System.out.println("Nenhuma compra encontrada com esse ID. Tente novamente.");
			} else {
				printSaleSummary(sale, false);
			}

			System.out.println("1 - Consultar mais uma compra");
			System.out.println("0 - Sair");
			opcao = readInt(scan, "Escolha: ", 0, 1);
		}
	}

	private SaleEntity requstData(Scanner scan) {
		SaleEntity saleEntity = new SaleEntity();
		geraId++;
		saleEntity.setId(Long.valueOf(geraId));

		int opcao = -1;

		do {
			System.out.println("1 - Adicionar item");
			System.out.println("2 - Adicionar cupom");
			System.out.println("3 - Finalizar compra");
			System.out.println("0 - Sair");
			System.out.print("Escolha: ");

			opcao = readInt(scan, "Escolha: ", 0, 3);

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
					finalizeSale(saleEntity);
					return saleEntity;
				case 0:
					return null;
				default:
					System.out.println("Opção inválida. Tente novamente.");
					break;
			}

		} while (opcao != 0);

		return null;
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

		int quantidade = readInt(scan, "Quantidade: ", 1, Integer.MAX_VALUE);

		item.setQuantity(quantidade);
		saleEntity.addItem(item);
	}


	private void setPayment(SaleEntity saleEntity, Scanner scan) {
		scan.nextLine();

		int paymentType = readInt(scan,
				"Informe a forma de pagamento, sendo:\n0 - PIX\n1 - CREDITO\n2 - BOLETO\nEscolha: ", 0, 2);

		saleEntity.setPaymentMethod(PaymentMethod.fromIndex(paymentType));

		if (paymentType == 1) {
			int quantity = readInt(scan, "Informe a quantidade de parcelas (1 a 12): ", 1, 12);
			saleEntity.setQuantityInstallments(quantity);
		}
	}


	private void finalizeSale(SaleEntity saleEntity) {
		printSaleSummary(saleEntity, true);
		sales.put(saleEntity.getId(), saleEntity);
	}


	private BigDecimal getValuePayment(BigDecimal totalFinal, SaleEntity saleEntity) {
		System.out.println(" - Método: " + saleEntity.getPaymentMethod());
		LocalDateTime currentDate = LocalDateTime.now();
		switch (saleEntity.getPaymentMethod()) {
			case PIX:
				BigDecimal discount = totalFinal.multiply(new BigDecimal("0.10"));
				System.out.println(" - Desconto de R$: " + discount + " (10%)");
				LocalDateTime timePix = currentDate.plusMinutes(30);
				System.out.println(" - Pagamento até as " + timePix.getHour() + ":" + timePix.getMinute() + " horas");
				return totalFinal.subtract(discount);
			case BOLETO:
				BigDecimal discountB = totalFinal.multiply(new BigDecimal("0.05"));
				System.out.println(" - Desconto de R$: " + discountB + " (5%)");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formatDate = currentDate.plusDays(3).format(formatter);
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

	private BigDecimal getValueProducts(SaleEntity sale) {
		return sale.getItems().stream()
				.map(item -> item.getProduct().getValue().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void setCoupon(SaleEntity saleEntity, Scanner scan) {
		if (saleEntity.getCoupon() != null) {
			System.out.println("Você já possui um cupom... deseja alterar?");
			int opcao = readInt(scan, "1 - Sim\n2 - Não\nEscolha: ", 1, 2);

			if (opcao == 1) {
				couponService.setCouponSale(saleEntity, scan);
			}
		} else {
			couponService.setCouponSale(saleEntity, scan);
		}
	}

	private int readInt(Scanner scan, String prompt, int min, int max) {
		int value;
		while (true) {
			System.out.print(prompt);
			if (scan.hasNextInt()) {
				value = scan.nextInt();
				if (value >= min && value <= max) return value;
				System.out.println("Valor fora do intervalo permitido.");
			} else {
				System.out.println("Entrada inválida. Digite um número.");
				scan.next();
			}
		}
	}

	private void printSaleSummary(SaleEntity sale, boolean isFinalized) {
		System.out.println("------ DETALHES DA COMPRA --------");
		System.out.println("ID: " + sale.getId());
		System.out.println("Produtos:");
		for (SaleItemEntity item : sale.getItems()) {
			System.out.println(item.getProduct() + " x " + item.getQuantity());
		}

		BigDecimal valueProducts = getValueProducts(sale);
		System.out.println("Subtotal: R$ " + valueProducts);

		CouponEntity couponEntity = sale.getCoupon();
		BigDecimal couponDiscount = couponService.applyCoupon(couponEntity, valueProducts);
		BigDecimal freight = getValueFreight(couponEntity, valueProducts);
		BigDecimal totalFinal = valueProducts.subtract(couponDiscount).add(freight);

		System.out.println("Total com Descontos: R$ " + totalFinal);

		if (isFinalized) {
			System.out.println("Pagamento:");
			BigDecimal totalPaid = getValuePayment(totalFinal, sale);
			sale.setValue(totalPaid);
			System.out.println("Total final: R$" + totalPaid);
			System.out.println("Status: Pedido Finalizado");
			System.out.println("--------------------------");
		} else {
			System.out.println("Forma de Pagamento: " + sale.getPaymentMethod());
			if (sale.getPaymentMethod() == PaymentMethod.CREDITO) {
				System.out.println("Parcelas: " + sale.getQuantityInstallments());
			}
			System.out.println("Valor pago: R$ " + sale.getValue());
			System.out.println("Status: Pedido Finalizado");
			System.out.println("----------------------------------");
		}
	}


}
