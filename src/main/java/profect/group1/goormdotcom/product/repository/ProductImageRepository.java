package profect.group1.goormdotcom.product.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import profect.group1.goormdotcom.product.repository.entity.ProductImageEntity;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, UUID>{
    
    List<ProductImageEntity> findByProductId(final UUID productId);

    long countByProductId(final UUID productId);

    void deleteAllByProductId(final UUID productId);
}
