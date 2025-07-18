package service;

import java.math.BigDecimal;
import java.util.Scanner;

import domain.CouponEntity;
import domain.SaleEntity;

/**
 * Serviço relacionado ao cupom
 *
 * @author Majú Testoni
 */
public interface CouponService {
	void setCouponSale(SaleEntity saleEntity, Scanner scan);

	BigDecimal applyCoupon(CouponEntity saleEntity, BigDecimal valueProducts);

	boolean isFreightFree(CouponEntity couponEntity);
}
