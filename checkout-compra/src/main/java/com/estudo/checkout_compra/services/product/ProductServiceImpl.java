package com.estudo.checkout_compra.services.product;

import org.springframework.stereotype.Service;

import com.estudo.checkout_compra.domain.product.ProductEntity;
import com.estudo.checkout_compra.dtos.product.ProductSaleDTO;
import com.estudo.checkout_compra.repositorys.product.ProductRepositoy;

/**
 * Implementação de {@link ProductService}
 *
 * @author Majú Testoni
 */
@Service
public class ProductServiceImpl  implements ProductService{

	private ProductRepositoy productRepositoy;

	public ProductServiceImpl(ProductRepositoy productRepositoy) {
		this.productRepositoy = productRepositoy;
	}

	@Override
	public ProductEntity saveProduct(ProductSaleDTO dto) {
		ProductEntity entity = new ProductEntity();

		entity.setName(dto.getName());
		entity.setCode(dto.getCodeProduct());
		entity.setValue(dto.getValue());

		entity = productRepositoy.save(entity);
		return entity;
	}

	@Override
	public ProductEntity findOrCreatedProduct(ProductSaleDTO dto) {
		return productRepositoy.findByCode(dto.getCodeProduct())
				.orElseGet(() -> saveProduct(dto));
	}
}
