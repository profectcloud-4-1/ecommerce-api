package profect.group1.goormdotcom.delivery.repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.delivery.repository.entity.GoormAddressEntity;

public interface GoormAddressRepository extends JpaRepository<GoormAddressEntity, UUID> {
    Optional<GoormAddressEntity> findTopByOrderByCreatedAtDesc();
}