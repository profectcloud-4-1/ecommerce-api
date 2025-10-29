package profect.group1.goormdotcom.order.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import profect.group1.goormdotcom.order.repository.entity.OrderAddressEntity;

public interface OrderAddressRepository extends JpaRepository<OrderAddressEntity, UUID> {
    Optional<OrderAddressEntity> findByOrderId(UUID orderId);
}

