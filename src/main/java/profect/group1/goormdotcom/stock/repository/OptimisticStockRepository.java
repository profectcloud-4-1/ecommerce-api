package profect.group1.goormdotcom.stock.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import profect.group1.goormdotcom.stock.repository.entity.OptimisticStockEntity;

public interface OptimisticStockRepository extends JpaRepository<OptimisticStockEntity, UUID> {
    public Optional<OptimisticStockEntity> findByProductId(UUID productId);
    
    public OptimisticStockEntity deleteByProductId(UUID productId);
}
