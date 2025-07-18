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

		System.out.println("1. Iniciar nova compra");
		System.out.println("0. Sair");
		System.out.print("Escolha: ");
		int opcao = scan.nextInt();

		while (opcao != 0) {
			switch (opcao) {
				case 1:
					saleService.newSale(scan);
					break;
				default:
					System.out.println("Opção inválida!");
					break;
			}


			System.out.println("1. Iniciar nova compra");
			System.out.println("0. Sair");
			System.out.print("Escolha: ");
			opcao = scan.nextInt();
		}

		System.out.println("======== Fim aplicação ========");
	}
}