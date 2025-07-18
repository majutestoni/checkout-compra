package service;

import java.util.Scanner;

import domain.ProductEntity;

/**
 * Serviço relacionado ao Produto
 *
 * @author Majú Testoni
 */
public interface ProductService {

	ProductEntity newProduct(Scanner scan);
}
