package profect.group1.goormdotcom.delivery.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryReturnAddressEntity;

public interface DeliveryReturnAddressRepository extends JpaRepository<DeliveryReturnAddressEntity, UUID> {

	Optional<DeliveryReturnAddressEntity> findByDeliveryReturnId(UUID deliveryReturnId);
}
