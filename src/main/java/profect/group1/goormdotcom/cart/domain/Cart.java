package profect.group1.goormdotcom.cart.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

	private UUID id;
	private UUID customerId;
	private int totalQuantity;
	private int totalPrice;
	private List<CartItem> items = new ArrayList<>();
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Cart(
			final UUID id,
			final UUID customerId,
			final List<CartItem> items,
			final LocalDateTime createdAt,
			final LocalDateTime updatedAt
	) {
		this.id = id;
		this.customerId = customerId;
		this.items = List.copyOf(items);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;

		calculateTotal();
	}

	public Cart addItem(final CartItem item) {
		List<CartItem> newItems = new ArrayList<>(this.items);
		Optional<CartItem> existingItem = newItems.stream()
				.filter(i -> i.getProductId().equals(item.getProductId()))
				.findFirst();

		if (existingItem.isPresent()) {
			CartItem existingCartItem = existingItem.get();
			CartItem newCartItem = existingCartItem.addQuantity(item.getQuantity());

			newItems.set(newItems.indexOf(existingCartItem), newCartItem);
		} else {
			newItems.add(item);
		}

		return new Cart(this.id, this.customerId, newItems, this.createdAt, this.updatedAt);
	}

	public Cart updateItem(final UUID cartItemId, final int quantity) {
		List<CartItem> newItems = new ArrayList<>(this.items);
		Optional<CartItem> existingItem = newItems.stream()
				.filter(i -> i.getId().equals(cartItemId))
				.findFirst();

		if (existingItem.isEmpty()) {
			throw new IllegalArgumentException("Item not exists");
		}

		CartItem existingCartItem = existingItem.get();
		CartItem updatedCartItem = existingCartItem.withQuantity(quantity);

		newItems.set(newItems.indexOf(existingCartItem), updatedCartItem);

		return new Cart(this.id, this.customerId, newItems, this.createdAt, this.updatedAt);
	}

	public CartItem getCartItem(final UUID cartItemId) {
		return items.stream().filter(e -> e.getId().equals(cartItemId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Item not exists"));
	}

	public CartItem getCartItemByProductId(final UUID productId) {
		return items.stream().filter(e -> e.getProductId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Item not exists"));
	}

	public Cart deleteItemById(final UUID itemId) {
		List<CartItem> newItems = new ArrayList<>(this.items);
		newItems.removeIf(i -> i.getId().equals(itemId));

		return new Cart(this.id, this.customerId, newItems, this.createdAt, this.updatedAt);
	}

	public Cart deleteBulkItem(final List<UUID> cartItemIds) {
		List<CartItem> newItems = new ArrayList<>(this.items);
		newItems.removeIf(item -> cartItemIds.contains(item.getId()));

		return new Cart(this.id, this.customerId, newItems, this.createdAt, this.updatedAt);
	}

	public Cart clear() {
		List<CartItem> newItems = new ArrayList<>();

		return new Cart(this.id, this.customerId, newItems, this.createdAt, this.updatedAt);
	}

	public void calculateTotal() {
		this.totalQuantity = items.stream().mapToInt(CartItem::getQuantity).sum();
		this.totalPrice = items.stream()
				.mapToInt(item -> item.getPrice() * item.getQuantity())
				.sum();
	}

	public List<CartItem> getItems() {
		return new ArrayList<>(items);
	}

	public Cart withNewItem(final CartItem newCartItem) {
		List<CartItem> newItems = new ArrayList<>(this.items);
		CartItem cartItem = getCartItemByProductId(newCartItem.getProductId());

		newItems.set(newItems.indexOf(cartItem), newCartItem);

		return new Cart(this.id, this.customerId, newItems, this.createdAt, this.updatedAt);
	}
}
