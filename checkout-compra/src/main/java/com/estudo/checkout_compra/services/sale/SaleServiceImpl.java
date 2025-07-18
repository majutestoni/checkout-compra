package com.estudo.checkout_compra.services.sale;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.estudo.checkout_compra.domain.coupon.CouponEntity;
import com.estudo.checkout_compra.domain.product.ProductEntity;
import com.estudo.checkout_compra.domain.sale.PaymentMethod;
import com.estudo.checkout_compra.domain.sale.SaleEntity;
import com.estudo.checkout_compra.domain.sale.SaleItemEntity;
import com.estudo.checkout_compra.dtos.ProductSaleDTO;
import com.estudo.checkout_compra.dtos.SaleDTO;
import com.estudo.checkout_compra.repositorys.sale.SaleRepository;
import com.estudo.checkout_compra.services.coupon.CouponService;
import com.estudo.checkout_compra.services.product.ProductService;

/**
 * Implementação de {@link SaleService}
 *
 * @author Majú Testoni
 */
@Service
public class SaleServiceImpl implements SaleService {

	private ProductService productService;
	private CouponService couponService;
	private SaleRepository saleRepository;

	public SaleServiceImpl(ProductService productService, CouponService couponService, SaleRepository saleRepository) {
		this.productService = productService;
		this.couponService = couponService;
		this.saleRepository = saleRepository;
	}

	@Override
	public SaleEntity newSale(SaleDTO dto) {
		SaleEntity entity = new SaleEntity();
		boolean whithFreight = true;

		entity.setPaymentMethod(PaymentMethod.fromIndex(dto.getPaymentMethod()));

		if (dto.getCodeCoupon() != null && !dto.getCodeCoupon().isBlank()) {
			CouponEntity couponByCode = couponService.getCouponByCode(dto.getCodeCoupon());
			if (couponByCode == null) {
				System.out.println("Cupom com o código: " + dto.getCodeCoupon() + " não foi encontrado");
			} else {
				whithFreight = !couponByCode.isFreight();
				entity.setCoupon(couponByCode);
			}
		}

		BigDecimal finalValue = BigDecimal.ZERO;
		for (ProductSaleDTO product: dto.getProductSaleDTOS()) {
			ProductEntity productEntity = productService.findOrCreatedProduct(product);

			SaleItemEntity item = new SaleItemEntity();
			item.setProduct(productEntity);
			item.setQuantity(product.getQuantity());
			item.setSale(entity);

			finalValue = finalValue.add(
					productEntity.getValue().multiply(BigDecimal.valueOf(item.getQuantity()))
			);

			entity.addItem(item);
		}

		if (whithFreight || finalValue.compareTo(BigDecimal.valueOf(250.00)) <= 0) {
			entity.setValueFreight(BigDecimal.valueOf(20));
		} else {
			entity.setValueFreight(BigDecimal.ZERO);
		}

		entity.setValue(finalValue);
		setValueFees(entity);


		entity = saleRepository.save(entity);
		return entity;
	}

	private void setValueFees(SaleEntity entity) {
		BigDecimal baseValue = entity.getValue();
		BigDecimal discount = BigDecimal.ZERO;

		switch (entity.getPaymentMethod()) {
			case CREDITO:
				Integer installments = entity.getQuantityInstallments();
				if (installments == null || installments < 1 || installments > 12) {
					System.out.println("\"Número de parcelas inválido. Deve ser entre 1 e 12.\"");
					return;
				}
				if (installments >= 4) {
					double taxa = 0.02;
					double totalComJuros = baseValue.doubleValue() * Math.pow(1 + taxa, installments);
					BigDecimal valorComJuros = BigDecimal.valueOf(totalComJuros).setScale(2, RoundingMode.HALF_UP);
					entity.setValueFees(valorComJuros.subtract(baseValue));
				}
				break;

			case BOLETO:
				discount = baseValue.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
				// LocalDate vencimento = LocalDate.now().plusDays(5); // considerando 3 dias úteis (pode ajustar)
				// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				// entity.setValueDiscount(discount);
				break;

			case PIX:
				discount = baseValue.multiply(BigDecimal.valueOf(0.10)).setScale(2, RoundingMode.HALF_UP);
				entity.setValueDiscount(discount);
				break;

			default:
		}
	}
}
