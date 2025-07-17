package com.estudo.checkout_compra.repositorys.coupon;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.checkout_compra.domain.coupon.CouponEntity;

/**
 * @author Majú Testoni
 */
@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

	Optional<CouponEntity> findByCode(String code);

}
