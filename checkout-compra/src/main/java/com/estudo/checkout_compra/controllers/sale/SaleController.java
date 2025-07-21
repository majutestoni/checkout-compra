package com.estudo.checkout_compra.controllers.sale;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.checkout_compra.dtos.sale.SaleDTO;
import com.estudo.checkout_compra.services.sale.SaleService;

import jakarta.validation.Valid;

/**
 * @author Majú Testoni
 */
@RestController
@RequestMapping("/sale")
public class SaleController {

	private SaleService saleService;

	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}

	public ResponseEntity<SaleDTO> newSale(@RequestBody @Valid SaleDTO saleDTO) {
		this.saleService.newSale(saleDTO)

		return ResponseEntity.ok()
	}
}
