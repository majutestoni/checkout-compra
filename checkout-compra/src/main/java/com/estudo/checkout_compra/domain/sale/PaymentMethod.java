package com.estudo.checkout_compra.domain.sale;

/**
 * Enumerado para as formas de pagamento
 *
 * @author Majú Testoni
 */
public enum PaymentMethod {

	PIX,
	CREDITO,
	BOLETO;

	public static PaymentMethod fromIndex(int index) {
		if (index < 0 || index >= values().length) {
			throw new IllegalArgumentException("Índice inválido para PaymentMethod: " + index);
		}
		return values()[index];
	}

	@Override
	public String toString() {
		switch (this) {
			case PIX: return "Pix";
			case CREDITO: return "Cartão de Crédito";
			case BOLETO: return "Boleto Bancário";
			default: return name();
		}
	}
}
