package profect.group1.goormdotcom.delivery.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryReturnEntity;

public interface DeliveryReturnRepository extends JpaRepository<DeliveryReturnEntity, UUID> {

	Optional<DeliveryReturnEntity> findByDeliveryId(UUID deliveryId);
}
