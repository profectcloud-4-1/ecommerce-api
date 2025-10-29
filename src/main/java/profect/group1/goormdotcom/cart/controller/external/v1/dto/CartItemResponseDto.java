package profect.group1.goormdotcom.cart.controller.external.v1.dto;

import java.util.UUID;

public record CartItemResponseDto(
		UUID id,
		UUID cartId,
		UUID productId,
		int quantity,
		int price
) {

}
