package profect.group1.goormdotcom.cart.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.cart.repository.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {

	Optional<CartEntity> findByCustomerId(UUID customerId);

	boolean existsByCustomerId(UUID customerId);
}
