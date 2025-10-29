package profect.group1.goormdotcom.user.repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.user.repository.entity.UserAddressEntity;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, UUID> {
	Optional<UserAddressEntity> findByUserId(UUID userId);
    List<UserAddressEntity> findAllByUserId(UUID userId);
}