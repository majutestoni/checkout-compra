package domain;

/**
 * Enumerado que representa formas de pagamento
 *
 * @author Majú Testoni
 */
public enum PaymentMethod {

	PIX,
	CREDITO,
	BOLETO;

	public static PaymentMethod fromIndex(int index) {
		if (index < 0 || index >= values().length) {
			System.out.println("Indice inválid!");
			return null;
		}
		return values()[index];
	}

	@Override
	public String toString() {
		return switch (this) {
			case PIX -> "PIX";
			case CREDITO -> "Cartão de Crédito";
			case BOLETO -> "Boleto Bancário";
		};
	}
}
