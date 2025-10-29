package profect.group1.goormdotcom.cart.controller.external.v1.dto;

import java.util.List;
import java.util.UUID;

public record CartResponseDto(
		UUID id,
		UUID customerId,
		int totalQuantity,
		int totalPrice,
		List<CartItemResponseDto> items
) {

}
