package profect.group1.goormdotcom.cart.service;

import java.util.List;
import java.util.UUID;
import profect.group1.goormdotcom.cart.domain.Cart;

public interface CartService {

	UUID createCart(UUID customerId);

	Cart getCart(UUID customerId);

	Cart addCartItem(UUID customerId, UUID productId, int quantity, int price);

	Cart updateCartItem(UUID customerId, UUID cartItemId, int quantity);

	Cart removeCartItem(UUID customerId, UUID cartItem);

	Cart removeBulkItem(UUID CustomerId, List<UUID> cartItemIds);

	Cart clearCart(UUID customerId);
}
