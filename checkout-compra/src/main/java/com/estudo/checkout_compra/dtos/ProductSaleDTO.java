package com.estudo.checkout_compra.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Majú Testoni
 */
public class ProductSaleDTO {

	@NotNull @NotBlank
	private String name;

	@NotNull @NotBlank
	private String codeProduct;

	@NotNull @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior que zero")
	private BigDecimal value;

	@NotNull
	@Min(value = 1, message = "A quantidade minima é 1")
	private Long quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeProduct() {
		return codeProduct;
	}

	public void setCodeProduct(String codeProduct) {
		this.codeProduct = codeProduct;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
