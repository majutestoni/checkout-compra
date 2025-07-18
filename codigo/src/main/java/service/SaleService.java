package service;

import java.util.Scanner;

import domain.SaleEntity;

/**
 * Serviço de compra/venda
 *
 * @author Majú Testoni
 */
public interface SaleService {

	void newSale(Scanner scan);

	void showData(Scanner scan);
}
