package com.estudo.checkout_compra.repositorys.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.checkout_compra.domain.sale.SaleEntity;

/**
 * @author Majú Testoni
 */
@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
