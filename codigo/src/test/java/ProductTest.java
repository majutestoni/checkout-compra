import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import domain.ProductEntity;
import service.ProductService;
import service.ProductServiceImpl;

/**
 * @author Majú Testoni
 */
public class ProductTest {

	@Test
	void newProduct_ValidInput_ReturnsProduct() {
		String input = "\nTeclado Mecânico\n150.00\nT123\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		ProductService productService = new ProductServiceImpl();
		ProductEntity product = productService.newProduct(scanner);

		assertEquals("Teclado Mecânico", product.getName());
		assertEquals(new BigDecimal("150.00"), product.getValue());
		assertEquals("T123", product.getCode());

		scanner.close();
	}

	@Test
	void newProduct_InvalidPriceThenValidPrice_ReturnsProduct() {
		String input = "\nMouse Gamer\n-5\n120.50\nM456\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		ProductService productService = new ProductServiceImpl();
		ProductEntity product = productService.newProduct(scanner);

		assertEquals("Mouse Gamer", product.getName());
		assertEquals(new BigDecimal("120.50"), product.getValue());
		assertEquals("M456", product.getCode());

		scanner.close();
	}

}
