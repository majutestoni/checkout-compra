import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import domain.CouponEntity;
import service.CouponServiceImpl;

/**
 * @author Majú Testoni
 */
public class CouponTest {

	private CouponServiceImpl couponService;
	private CouponEntity cupom10;
	private CouponEntity cupomFreteGratis;


	@BeforeEach
	void setup() {
		couponService = new CouponServiceImpl();

		cupom10 = new CouponEntity();
		cupom10.setCode("CUPOM10");

		cupomFreteGratis = new CouponEntity();
		cupomFreteGratis.setCode("CUPOMFRETEGRATIS");
	}


	@Test
	void testApplyCoupon10PercentDiscount() {
		BigDecimal valorProdutos = new BigDecimal("200.00");

		BigDecimal desconto = couponService.applyCoupon(cupom10, valorProdutos);

		assertNotNull(desconto, "Desconto não pode ser nulo");
		assertEquals(new BigDecimal("20.0000"), desconto.setScale(4), "Desconto deve ser 10% do valor dos produtos");
	}

	@Test
	void testApplyCouponFreteGratis() {
		BigDecimal valorProdutos = new BigDecimal("200.00");

		BigDecimal desconto = couponService.applyCoupon(cupomFreteGratis, valorProdutos);

		assertNotNull(desconto, "Desconto não pode ser nulo");
		assertEquals(BigDecimal.ZERO, desconto, "Desconto do cupom frete grátis deve ser zero (tratado no frete)");
	}

	@Test
	void testApplyCouponNullCoupon() {
		BigDecimal valorProdutos = new BigDecimal("100.00");

		BigDecimal desconto = couponService.applyCoupon(null, valorProdutos);

		assertEquals(BigDecimal.ZERO, desconto, "Desconto deve ser zero para cupom nulo");
	}

	@Test
	void testApplyCouponUnknownCode() {
		CouponEntity cupomInvalido = new CouponEntity();
		cupomInvalido.setCode("CUPOMINVALIDO");

		BigDecimal desconto = couponService.applyCoupon(cupomInvalido, new BigDecimal("100.00"));

		assertEquals(BigDecimal.ZERO, desconto, "Desconto deve ser zero para cupom desconhecido");
	}

	@Test
	void testIsFreightFreeTrue() {
		assertTrue(couponService.isFreightFree(cupomFreteGratis), "Deve identificar cupom de frete grátis");
	}

	@Test
	void testIsFreightFreeFalse() {
		assertFalse(couponService.isFreightFree(cupom10), "Não deve identificar cupom 10% como frete grátis");
	}

	@Test
	void testIsFreightFreeNullCoupon() {
		assertFalse(couponService.isFreightFree(null), "Cupom nulo não é frete grátis");
	}
}
