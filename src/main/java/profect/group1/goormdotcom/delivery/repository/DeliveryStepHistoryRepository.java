package profect.group1.goormdotcom.delivery.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryStepHistoryEntity;

public interface DeliveryStepHistoryRepository extends JpaRepository<DeliveryStepHistoryEntity, UUID> {

	List<DeliveryStepHistoryEntity> findAllByDeliveryIdOrderByCreatedAtDesc(UUID deliveryId);
}
