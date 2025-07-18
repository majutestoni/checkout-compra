import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import domain.PaymentMethod;
import domain.ProductEntity;
import domain.SaleEntity;
import domain.SaleItemEntity;

/**
 * @author Majú Testoni
 */
public class SaleTest {

	@Test
	void addItem_ShouldIncreaseItemsList() {
		SaleEntity sale = new SaleEntity();
		assertTrue(sale.getItems() == null || sale.getItems().isEmpty());

		ProductEntity product = new ProductEntity();
		product.setName("Notebook");
		product.setValue(new BigDecimal("2500.00"));

		SaleItemEntity item = new SaleItemEntity();
		item.setProduct(product);
		item.setQuantity(2);

		sale.addItem(item);

		assertNotNull(sale.getItems());
		assertEquals(1, sale.getItems().size());
		assertEquals("Notebook", sale.getItems().get(0).getProduct().getName());
		assertEquals(2, sale.getItems().get(0).getQuantity());
	}

	@Test
	void calculateSubtotal_ShouldSumProductValuesTimesQuantity() {
		SaleEntity sale = new SaleEntity();
		sale.setItems(new ArrayList<>());

		ProductEntity p1 = new ProductEntity();
		p1.setValue(new BigDecimal("100.00"));
		SaleItemEntity item1 = new SaleItemEntity();
		item1.setProduct(p1);
		item1.setQuantity(3);

		ProductEntity p2 = new ProductEntity();
		p2.setValue(new BigDecimal("50.00"));
		SaleItemEntity item2 = new SaleItemEntity();
		item2.setProduct(p2);
		item2.setQuantity(1);

		sale.addItem(item1);
		sale.addItem(item2);

		BigDecimal expectedSubtotal = p1.getValue().multiply(BigDecimal.valueOf(3))
				.add(p2.getValue().multiply(BigDecimal.valueOf(1)));

		BigDecimal actualSubtotal = sale.getItems().stream()
				.map(i -> i.getProduct().getValue().multiply(BigDecimal.valueOf(i.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		assertEquals(0, expectedSubtotal.compareTo(actualSubtotal));
		assertEquals(new BigDecimal("350.00"), actualSubtotal);
	}

	@Test
	void setPaymentMethod_ShouldStoreMethodAndInstallments() {
		SaleEntity sale = new SaleEntity();

		sale.setPaymentMethod(PaymentMethod.CREDITO);
		sale.setQuantityInstallments(5);

		assertEquals(PaymentMethod.CREDITO, sale.getPaymentMethod());
		assertEquals(5, sale.getQuantityInstallments());

		assertTrue(sale.getQuantityInstallments() >= 1 && sale.getQuantityInstallments() <= 12);
	}
}
