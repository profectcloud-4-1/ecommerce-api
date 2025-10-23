// package profect.group1.goormdotcom.order.repository;

// public interface OrderRepository {
// }
package profect.group1.goormdotcom.order.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.order.repository.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    OrderEntity getOrderEntityById(UUID Id);
}


