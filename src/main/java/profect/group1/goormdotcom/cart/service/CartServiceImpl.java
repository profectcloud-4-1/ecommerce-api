package profect.group1.goormdotcom.cart.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import profect.group1.goormdotcom.cart.domain.Cart;
import profect.group1.goormdotcom.cart.domain.CartItem;
import profect.group1.goormdotcom.cart.repository.CartItemRepository;
import profect.group1.goormdotcom.cart.repository.CartRepository;
import profect.group1.goormdotcom.cart.repository.entity.CartEntity;
import profect.group1.goormdotcom.cart.repository.entity.CartItemEntity;
import profect.group1.goormdotcom.cart.repository.mapper.CartItemMapper;
import profect.group1.goormdotcom.cart.repository.mapper.CartMapper;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public UUID createCart(final UUID customerId) {
		if(cartRepository.existsByCustomerId(customerId)) {
			throw new IllegalArgumentException("Customer id " + customerId + " already exists");
		}

		CartEntity savedEntity = cartRepository.save(
				new CartEntity(customerId)
		);

		return savedEntity.getId();
	}

	public Cart addCartItem(
			final UUID customerId,
			final UUID productId,
			final int quantity,
			final int price
	) {
		Cart cart = getCart(customerId);
		Cart savedCart = cart.addItem(new CartItem(cart.getId(), productId, quantity, price));
		CartItemEntity savedItem = cartItemRepository.save(CartItemMapper.toEntity(savedCart.getCartItemByProductId(productId)));

		return savedCart.withNewItem(CartItemMapper.toDomain(savedItem));
	}

	public Cart updateCartItem(
			final UUID customerId,
			final UUID cartItemId,
			final int quantity
	) {
		Cart cart = getCart(customerId);
		cart = cart.updateItem(cartItemId, quantity);

		CartItem cartItem = cart.getCartItem(cartItemId);
		cartItemRepository.save(CartItemMapper.toEntity(cartItem));

		return cart;
	}

	public Cart removeCartItem(
			final UUID customerId,
			final UUID cartItemId
	) {
		Cart cart = getCart(customerId);
		cart = cart.deleteItemById(cartItemId);

		cartItemRepository.deleteById(cartItemId);

		return cart;
	}

	public Cart removeBulkItem(
			final UUID customerId,
			final List<UUID> cartItemIds
	) {
		Cart cart = getCart(customerId);
		cart = cart.deleteBulkItem(cartItemIds);

		cartItemRepository.deleteAllByIdInBatch(cartItemIds);

		return cart;
	}

	public Cart clearCart(
			final UUID customerId
	) {
		Optional<CartEntity> cartEntity = cartRepository.findByCustomerId(customerId);

		if (cartEntity.isEmpty()) {
			throw new IllegalArgumentException("Cart not found");
		}

		CartEntity entity = cartEntity.get();
		cartItemRepository.deleteAllByCartId(entity.getId());

		Cart cart = CartMapper.toDomain(entity, List.of());
		cart = cart.clear();

		return cart;
	}

	@Transactional(readOnly = true)
	public Cart getCart(final UUID customerId) {
		CartEntity cartEntity = cartRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Cart not found"));

		List<CartItemEntity> cartItemEntities = cartItemRepository.findByCartId(cartEntity.getId());
		List<CartItem> cartItems = cartItemEntities.stream().map(CartItemMapper::toDomain).toList();

		return CartMapper.toDomain(cartEntity, cartItems);
	}
}
