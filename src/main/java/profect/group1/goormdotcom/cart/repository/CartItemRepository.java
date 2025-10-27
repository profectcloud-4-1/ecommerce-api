package profect.group1.goormdotcom.cart.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.cart.repository.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {

	List<CartItemEntity> findByCartId(UUID cartId);

	void deleteAllByCartId(UUID cartId);

	List<CartItemEntity> findByProductId(UUID productId);

	List<CartItemEntity> findByProductIdAndCartId(UUID productId, UUID cartId);
}
