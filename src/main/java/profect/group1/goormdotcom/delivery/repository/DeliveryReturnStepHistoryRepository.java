package profect.group1.goormdotcom.delivery.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryReturnStepHistoryEntity;

public interface DeliveryReturnStepHistoryRepository extends JpaRepository<DeliveryReturnStepHistoryEntity, UUID> {

	List<DeliveryReturnStepHistoryEntity> findAllByDeliveryReturnIdOrderByCreatedAtDesc(UUID deliveryReturnId);
}
