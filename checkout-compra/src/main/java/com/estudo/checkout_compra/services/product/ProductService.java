package com.estudo.checkout_compra.services.product;

import com.estudo.checkout_compra.domain.product.ProductEntity;
import com.estudo.checkout_compra.dtos.product.ProductSaleDTO;

/**
 * Serviço relaciona a entidade Produto
 *
 * @author Majú Testoni
 */
public interface ProductService {

	ProductEntity saveProduct(ProductSaleDTO dto);

	ProductEntity findOrCreatedProduct(ProductSaleDTO dto);
}
