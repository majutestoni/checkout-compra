package com.estudo.checkout_compra.domain.product;

import java.math.BigDecimal;

import com.estudo.checkout_compra.domain.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entitdade que representa um produto
 *
 * @author Majú Testoni
 */
@Entity
@Table(name = "products")
@AttributeOverride(name = "id", column = @Column(name = "id_product"))
public class ProductEntity extends BaseEntity {

	@Column(name = "nm_product", nullable = false)
	private String name;

	@Column(name = "cd_product", nullable = false, unique = true)
	private String code;

	@Column(name = "vl_product", nullable = false, precision = 19, scale = 2)
	private BigDecimal value;

	@Override
	public String toString() {
		return "- " + this.getName() + "(" +  this.getCode() + ")" + " - " + this.getValue() ;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
