package com.estudo.checkout_compra.domain.sale;

import com.estudo.checkout_compra.domain.BaseEntity;
import com.estudo.checkout_compra.domain.product.ProductEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

/**
 * representa um item (produto) da compra (sale)
 *
 * @author Majú Testoni
 */
@Entity
@Table(name = "sale_items")
@AttributeOverride(name = "id", column = @Column(name = "id_sale_item"))
public class SaleItemEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "sale_id_sale")
	private SaleEntity sale;

	@ManyToOne
	@JoinColumn(name = "product_id_product")
	private ProductEntity product;

	@Column(name = "qt_sale_item", nullable = false)
	@Min(value = 1, message = "É necessário informar a quantidade maior que 0")
	private Long quantity;


	public SaleEntity getSale() {
		return sale;
	}

	public void setSale(SaleEntity sale) {
		this.sale = sale;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
