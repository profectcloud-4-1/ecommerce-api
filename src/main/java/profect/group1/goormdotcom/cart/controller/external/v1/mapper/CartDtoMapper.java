package profect.group1.goormdotcom.cart.controller.external.v1.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.CartItemResponseDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.CartResponseDto;
import profect.group1.goormdotcom.cart.domain.Cart;
import profect.group1.goormdotcom.cart.domain.CartItem;

@Component
public class CartDtoMapper {

	public static CartResponseDto toCartDto(Cart cart) {
		return new CartResponseDto(
				cart.getId(),
				cart.getCustomerId(),
				cart.getTotalQuantity(),
				cart.getTotalPrice(),
				cart.getItems().stream().map(CartDtoMapper::toItemDto).toList()
		);
	}

	public static CartItemResponseDto toItemDto(CartItem cartItem) {
		return new CartItemResponseDto(
				cartItem.getId(),
				cartItem.getCartId(),
				cartItem.getProductId(),
				cartItem.getQuantity(),
				cartItem.getPrice()
		);
	}
}
