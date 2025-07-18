package com.estudo.checkout_compra.domain.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.estudo.checkout_compra.domain.BaseEntity;
import com.estudo.checkout_compra.domain.coupon.CouponEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade que representa uma compra do usuario/venda do sistema
 *
 * @author Majú Testoni
 */
@Entity
@Table(name = "sales")
@AttributeOverride(name = "id", column = @Column(name = "id_sale"))
public class SaleEntity extends BaseEntity {

	@Column(name = "vl_sale", nullable = false, precision = 19, scale = 2)
	private BigDecimal value;

	@Column(name = "vl_sale_freught", nullable = false, precision = 19, scale = 2)
	private BigDecimal valueFreight;

	@Column(name = "vl_sale_discout", precision = 19, scale = 2)
	private BigDecimal valueDiscount;

	@Column(name = "vl_sale_Fees", precision = 19, scale = 2)
	private BigDecimal valueFees;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tp_payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "qt_installments")
	private Integer quantityInstallments;

	@ManyToOne
	@JoinColumn(name = "coupon_id_coupon")
	private CouponEntity coupon;

	@NotNull @NotEmpty
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleItemEntity> items = new ArrayList<>();

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public CouponEntity getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponEntity coupon) {
		this.coupon = coupon;
	}

	public List<SaleItemEntity> getItems() {
		return items;
	}

	public void setItems(List<SaleItemEntity> items) {
		this.items = items;
	}

	public void addItem(SaleItemEntity item) {
		this.items.add(item);
	}

	public BigDecimal getValueFreight() {
		return valueFreight;
	}

	public void setValueFreight(BigDecimal valueFreight) {
		this.valueFreight = valueFreight;
	}

	public Integer getQuantityInstallments() {
		return quantityInstallments;
	}

	public void setQuantityInstallments(Integer quantityInstallments) {
		this.quantityInstallments = quantityInstallments;
	}

	public BigDecimal getValueDiscount() {
		return valueDiscount;
	}

	public void setValueDiscount(BigDecimal valueDiscount) {
		this.valueDiscount = valueDiscount;
	}

	public BigDecimal getValueFees() {
		return valueFees;
	}

	public void setValueFees(BigDecimal valueFees) {
		this.valueFees = valueFees;
	}
}
