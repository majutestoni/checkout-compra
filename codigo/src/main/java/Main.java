import java.util.Scanner;

import service.CouponService;
import service.CouponServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import service.SaleService;
import service.SaleServiceImpl;

/**
 * @author Majú Testoni
 */
public class Main {

	private static SaleService saleService;

	public Main(SaleService saleService) {
		this.saleService = saleService;
	}

	public static void main(String[] args) {
		CouponService couponService = new CouponServiceImpl();
		ProductService productService = new ProductServiceImpl();
		SaleService saleService = new SaleServiceImpl(couponService, productService);

		System.out.println("======== Iniciando aplicação ========");

		Scanner scan = new Scanner(System.in);
		int opcao = 10000;

		while (opcao != 0) {
			switch (opcao) {
				case 1:
					saleService.newSale(scan);
					break;
				case 2:
					saleService.showData(scan);
					break;
				default:
					System.out.println("Opção inválida!");
					break;
			}


			System.out.println("1. Iniciar nova compra");
			System.out.println("2. Consultar uma compra");
			System.out.println("0. Sair");
			opcao = readInt(scan, "Escolha:", 0, 2);
		}

		System.out.println("======== Fim aplicação ========");
	}

	private static int readInt(Scanner scan, String prompt, int min, int max) {
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
}