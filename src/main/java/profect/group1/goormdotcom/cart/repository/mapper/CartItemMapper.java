package profect.group1.goormdotcom.cart.repository.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.cart.domain.CartItem;
import profect.group1.goormdotcom.cart.repository.entity.CartItemEntity;

@Component
public class CartItemMapper {

	public static CartItem toDomain(final CartItemEntity entity) {
		return new CartItem(
				entity.getId(),
				entity.getCartId(),
				entity.getProductId(),
				entity.getQuantity(),
				entity.getPrice(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getDeletedAt());
	}

	public static CartItemEntity toEntity(final CartItem item) {
		return new CartItemEntity(
				item.getId(),
				item.getCartId(),
				item.getProductId(),
				item.getQuantity(),
				item.getPrice()
		);
	}

}
