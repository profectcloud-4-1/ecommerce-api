package profect.group1.goormdotcom.delivery.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryAddressEntity;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddressEntity, UUID> {

	Optional<DeliveryAddressEntity> findByDeliveryId(UUID deliveryId);
}
