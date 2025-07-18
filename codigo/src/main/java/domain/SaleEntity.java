package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma venda/compra
 *
 * @author Majú Testoni
 */
public class SaleEntity extends BaseEntity{

	private BigDecimal value;

	private PaymentMethod paymentMethod;

	private CouponEntity coupon;


	private int quantityInstallments;

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

	public List<SaleItemEntity> getItems() {
		return items;
	}

	public void setItems(List<SaleItemEntity> items) {
		this.items = items;
	}

	public void addItem(SaleItemEntity item) {
		this.items.add(item);
	}

	public CouponEntity getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponEntity coupon) {
		this.coupon = coupon;
	}

	public int getQuantityInstallments() {
		return quantityInstallments;
	}

	public void setQuantityInstallments(int quantityInstallments) {
		this.quantityInstallments = quantityInstallments;
	}
}
