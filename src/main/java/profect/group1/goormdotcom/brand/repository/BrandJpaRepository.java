package profect.group1.goormdotcom.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import profect.group1.goormdotcom.brand.repository.entity.BrandEntity;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;


public interface BrandJpaRepository extends JpaRepository<BrandEntity, UUID> {
}
