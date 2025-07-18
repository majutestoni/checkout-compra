package service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import domain.CouponEntity;
import domain.SaleEntity;

/**
 * Implementação de {@link CouponService}
 *
 * @author Majú Testoni
 */
public class CouponServiceImpl implements CouponService{

	private final Map<String, CouponEntity> availableCoupons = new HashMap<>();

	public CouponServiceImpl() {
		CouponEntity freteGratis = new CouponEntity();
		freteGratis.setId(1L);
		freteGratis.setCode("CUPOMFRETEGRATIS");

		CouponEntity desconto10 = new CouponEntity();
		desconto10.setId(2L);
		desconto10.setCode("CUPOM10");

		availableCoupons.put(freteGratis.getCode() ,freteGratis);
		availableCoupons.put(desconto10.getCode(), desconto10);
	}


	@Override
	public void setCouponSale(SaleEntity saleEntity, Scanner scan) {
		int opcao = 1;

		while (opcao == 1) {
			scan.nextLine();
			System.out.println("Informe o código do cupom:");
			String code = scan.nextLine().toUpperCase();

			if (availableCoupons.containsKey(code)) {
				saleEntity.setCoupon(availableCoupons.get(code));
				System.out.println("Cupom adicionado!");
				break;
			} else {
				System.out.println("Cupom não encontrado... deseja informar outro?");

				boolean invalidInput = false;
				while (!invalidInput) {
					System.out.print("1 - Sim\n2 - Não\nEscolha: ");
					if (scan.hasNextInt()) {
						opcao = scan.nextInt();
						if (opcao == 1 || opcao == 2) {
							invalidInput = true;
						} else {
							System.out.println("Opção inválida. Digite 1 para Sim ou 2 para Não.");
						}
					} else {
						System.out.println("Entrada inválida. Digite um número (1 ou 2).");
						scan.next();
					}
				}
			}
		}
	}


	@Override
	public BigDecimal applyCoupon(CouponEntity coupon, BigDecimal valueProducts) {
		    if (coupon == null || coupon.getCode() == null) return BigDecimal.ZERO;

			String codeCoupon = coupon.getCode();
			switch (codeCoupon) {
				case "CUPOM10":
					BigDecimal value = valueProducts.multiply(new BigDecimal("0.10"));
					System.out.println("Cupom Aplicado: " + codeCoupon + "(R$" + value + ")");
					return value;
				case "CUPOMFRETEGRATIS":
					System.out.println("Cupom Aplicado: " + codeCoupon + "(R$" + "20,00" + ")");
					return BigDecimal.ZERO; // o tratamento é feito no calculo do frete
				default:
					System.out.println("Cupom não reconhecido ou inválido.");
					return BigDecimal.ZERO;
			}
	}

	@Override
	public boolean isFreightFree(CouponEntity couponEntity) {
		return couponEntity != null && Objects.equals("CUPOMFRETEGRATIS", couponEntity.getCode());
	}

}
