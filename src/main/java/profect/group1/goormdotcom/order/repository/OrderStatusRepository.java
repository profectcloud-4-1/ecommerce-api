package profect.group1.goormdotcom.order.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.order.repository.entity.OrderStatusEntity;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, UUID> {
    Optional<OrderStatusEntity> findTop1ByOrder_IdOrderByCreatedAtDesc(UUID orderId);
}
