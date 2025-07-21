package com.estudo.checkout_compra.dtos.sale;

import java.util.ArrayList;
import java.util.List;

import com.estudo.checkout_compra.dtos.product.ProductSaleDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * @author Majú Testoni
 */
public class SaleDTO {

	@NotNull
	private int paymentMethod;

	private String codeCoupon;

	private int quantityInstallments;

	@NotNull @NotEmpty
	private List<ProductSaleDTO> productSaleDTOS = new ArrayList<>();

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCodeCoupon() {
		return codeCoupon;
	}

	public void setCodeCoupon(String codeCoupon) {
		this.codeCoupon = codeCoupon;
	}

	public List<ProductSaleDTO> getProductSaleDTOS() {
		return productSaleDTOS;
	}

	public void setProductSaleDTOS(List<ProductSaleDTO> productSaleDTOS) {
		this.productSaleDTOS = productSaleDTOS;
	}

	public void addProduct(ProductSaleDTO productSaleDTO) {
		this.productSaleDTOS.add(productSaleDTO);
	}

	public int getQuantityInstallments() {
		return quantityInstallments;
	}

	public void setQuantityInstallments(int quantityInstallments) {
		this.quantityInstallments = quantityInstallments;
	}


}
