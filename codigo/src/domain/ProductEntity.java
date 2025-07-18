package domain;

import java.math.BigDecimal;

/**
 * Entidade que representa um produto a ser comprado
 *
 * @author Majú Testoni
 */
public class ProductEntity extends BaseEntity {

	private String name;
	private String code;
	private BigDecimal value;

	@Override
	public String toString() {
		return "- " +
				name + " (" + code + ")" +
				" - R$" + value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
