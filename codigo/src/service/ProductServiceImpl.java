package service;

import java.math.BigDecimal;
import java.util.Scanner;

import domain.ProductEntity;

/**
 * Implementação de {@link ProductService}
 *
 * @author Majú Testoni
 */
public class ProductServiceImpl implements ProductService {

	@Override
	public ProductEntity newProduct(Scanner scan) {
		scan.nextLine();
		ProductEntity productEntity = new ProductEntity();

		String name = "";
		while (name.isBlank()) {
			System.out.print("Nome: ");
			name = scan.nextLine();
			if (name.isBlank()) {
				System.out.println("Nome não pode ser vazio. Tente novamente.");
			}
		}
		productEntity.setName(name);

		BigDecimal value = null;
		while (value == null) {
			System.out.print("Preço: ");
			String valueStr = scan.nextLine();
			try {
				value = new BigDecimal(valueStr);
				if (value.compareTo(BigDecimal.ZERO) < 0) {
					System.out.println("Preço deve ser positivo.");
					value = null;
				}
			} catch (NumberFormatException e) {
				System.out.println("Formato de preço inválido. Tente novamente.");
			}
		}
		productEntity.setValue(value);

		String code = "";
		while (code.isBlank()) {
			System.out.print("Código do produto: ");
			code = scan.nextLine();
			if (code.isBlank()) {
				System.out.println("Código não pode ser vazio. Tente novamente.");
			}
		}
		productEntity.setCode(code);

		return productEntity;
	}

}
