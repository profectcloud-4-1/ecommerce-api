package profect.group1.goormdotcom.delivery.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryEntity;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, UUID> {

	Optional<DeliveryEntity> findByOrderId(UUID orderId);
}
