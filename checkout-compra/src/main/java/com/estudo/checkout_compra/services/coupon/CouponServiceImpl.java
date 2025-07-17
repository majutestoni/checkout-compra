package com.estudo.checkout_compra.services.coupon;

import org.springframework.stereotype.Service;

import com.estudo.checkout_compra.domain.coupon.CouponEntity;
import com.estudo.checkout_compra.repositorys.coupon.CouponRepository;

/**
 * @author Majú Testoni
 */
@Service
public class CouponServiceImpl implements CouponService {

	private CouponRepository couponRepository;

	public CouponServiceImpl(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}

	@Override
	public CouponEntity getCouponByCode(String code) {
		code = code.toUpperCase();

		return couponRepository.findByCode(code).orElse(null);
	}
}
