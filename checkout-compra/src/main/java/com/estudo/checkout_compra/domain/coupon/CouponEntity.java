package com.estudo.checkout_compra.domain.coupon;

import java.math.BigDecimal;

import com.estudo.checkout_compra.domain.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entidade que reprensenta um cupom que pode ser utilizando em compra (sale)
 *
 * @author Majú Testoni
 */

@Entity
@Table(name = "coupons")
@AttributeOverride(name = "id", column = @Column(name = "id_coupon"))
public class CouponEntity extends BaseEntity {

	@Column(name = "cd_coupon", nullable = false, unique = true, length = 100)
	private String code;

	@Column(name = "vl_discount", nullable = false, precision = 19, scale = 2)
	private BigDecimal discount;

	@Column(name = "is_freight")
	private boolean freight = false;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public boolean isFreight() {
		return freight;
	}

	public void setFreight(boolean freight) {
		this.freight = freight;
	}
}
