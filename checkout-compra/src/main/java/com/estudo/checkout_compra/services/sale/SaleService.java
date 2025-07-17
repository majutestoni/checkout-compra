package com.estudo.checkout_compra.services.sale;

import com.estudo.checkout_compra.domain.sale.SaleEntity;
import com.estudo.checkout_compra.dtos.SaleDTO;

/**
 * Serviço utilizando para as compras
 *
 * @author Majú Testoni
 */
public interface SaleService {

	SaleEntity newSale(SaleDTO dto);

}
