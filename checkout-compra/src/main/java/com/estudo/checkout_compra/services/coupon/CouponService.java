package com.estudo.checkout_compra.services.coupon;

import com.estudo.checkout_compra.domain.coupon.CouponEntity;

/**
 * @author Majú Testoni
 */
public interface CouponService {

	CouponEntity getCouponByCode(String code);
}
