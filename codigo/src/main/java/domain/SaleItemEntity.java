package domain;

/**
 * Entidade de representa um item da compra, com seu produto e quantidade
 *
 * @author Majú Testoni
 */
public class SaleItemEntity extends BaseEntity {
	private ProductEntity product;
	private SaleEntity sale;
	private int quantity;

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public SaleEntity getSale() {
		return sale;
	}

	public void setSale(SaleEntity sale) {
		this.sale = sale;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
