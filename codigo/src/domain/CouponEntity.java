package domain;

/**
 * Entidade que representa um cupom
 *
 * @author Majú Testoni
 */
public class CouponEntity extends BaseEntity{

	private Long id;

	private String code;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
