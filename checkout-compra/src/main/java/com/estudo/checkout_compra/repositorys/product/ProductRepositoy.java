package com.estudo.checkout_compra.repositorys.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.checkout_compra.domain.product.ProductEntity;

/**
 * @author Majú Testoni
 */
@Repository
public interface ProductRepositoy extends JpaRepository<ProductEntity, Long> {

	Optional<ProductEntity> findByCode(String code);
}
