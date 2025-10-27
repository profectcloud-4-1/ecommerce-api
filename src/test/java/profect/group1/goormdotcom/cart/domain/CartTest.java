package profect.group1.goormdotcom.cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

	private UUID cartId;
	private UUID customerId;
	private CartItem itemA;
	private CartItem itemB;
	private LocalDateTime now;

	@BeforeEach
	void setUp() {
		now = LocalDateTime.now();
		cartId = UUID.randomUUID();
		customerId = UUID.randomUUID();

		itemA = new CartItem(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 3, 3, now,
				now, now);
		itemB = new CartItem(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 3, 1, now,
				now, now);
	}

	@Test
	@DisplayName("새로운 아이템을 추가하면 장바구니에 포함된다")
	void addItem_newItem() {
		//given
		Cart cart = new Cart(cartId, customerId, List.of(), now, now);

		//when
		Cart updated = cart.addItem(itemA);

		//then
		assertThat(updated.getItems()).hasSize(1);
		assertThat(updated.getItems().get(0).getProductId()).isEqualTo(itemA.getProductId());
	}

	@Test
	@DisplayName("같은 상품을 추가하면 수량이 합쳐진다")
	void addItem_existingItemMergesQuantity() {
		//given
		CartItem sameProduct = new CartItem(UUID.randomUUID(), itemA.getProductId(), 2, 3);
		Cart cart = new Cart(cartId, customerId, List.of(itemA), now, now);

		//then
		Cart updated = cart.addItem(sameProduct);
		CartItem merged = updated.getCartItemByProductId(itemA.getProductId());

		assertThat(merged.getQuantity()).isEqualTo(5);
	}

	@Test
	@DisplayName("아이템 수량을 변경할 수 있다")
	void updateItem_quantity() {
		//given
		Cart cart = new Cart(cartId, customerId, List.of(itemA), now, now);

		//when
		Cart updated = cart.updateItem(itemA.getId(), 10);

		//then
		assertThat(updated.getCartItem(itemA.getId()).getQuantity()).isEqualTo(10);
	}

	@Test
	@DisplayName("존재하지 않는 아이템을 수정하려 하면 예외 발생")
	void updateItem_notExists_throws() {
		//given
		Cart cart = new Cart(cartId, customerId, List.of(itemA), now, now);

		assertThatThrownBy(() -> cart.updateItem(UUID.randomUUID(), 5))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Item not exists");
	}

	@Test
	@DisplayName("아이템을 개별 삭제할 수 있다")
	void deleteItemById() {
		Cart cart = new Cart(cartId, customerId, List.of(itemA, itemB), now, now);
		Cart updated = cart.deleteItemById(itemA.getId());

		assertThat(updated.getItems()).hasSize(1);
	}

	@Test
	@DisplayName("여러 아이템을 일괄 삭제할 수 있다")
	void deleteBulkItem() {
		Cart cart = new Cart(cartId, customerId, List.of(itemA, itemB), now, now);
		Cart updated = cart.deleteBulkItem(List.of(itemA.getId(), itemB.getId()));

		assertThat(updated.getItems()).isEmpty();
	}

	@Test
	@DisplayName("장바구니를 비울 수 있다")
	void clear() {
		Cart cart = new Cart(cartId, customerId, List.of(itemA, itemB), now, now);
		Cart cleared = cart.clear();

		assertThat(cleared.getItems()).isEmpty();
	}

	@Test
	@DisplayName("총 가격과 수량을 계산할 수 있다")
	void calculateTotal() {
		Cart cart = new Cart(cartId, customerId, List.of(itemA, itemB), now, now);

		assertThat(cart.getTotalQuantity()).isEqualTo(6);
		assertThat(cart.getTotalPrice()).isEqualTo(3 * 3 + 3 * 1);
	}
}
