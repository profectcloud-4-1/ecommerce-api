package profect.group1.goormdotcom.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.stock.domain.Stock;
import profect.group1.goormdotcom.stock.repository.OptimisticStockRepository;
import profect.group1.goormdotcom.stock.repository.StockRepository;
import profect.group1.goormdotcom.stock.repository.entity.OptimisticStockEntity;
import profect.group1.goormdotcom.stock.repository.entity.StockEntity;
import profect.group1.goormdotcom.stock.repository.mapper.StockMapper;

@Service
@RequiredArgsConstructor
public class StockService {
    
    private final StockRepository stockRepository;
    private final OptimisticStockRepository optimisticStockRepository;

    private StockEntity getStockEntity(UUID productId) {
        Optional<StockEntity> stockEntity = stockRepository.findByProductId(productId);
        
        if (stockEntity.isEmpty()) {
			throw new IllegalArgumentException("Product not found");
		}
        return stockEntity.get(); 
    }

    @Transactional
    public Stock registerStock(UUID productId, int stockQuantity) {
        stockRepository.findByProductId(productId)
            .ifPresent(stock -> { throw new IllegalArgumentException("Stock already exists");});

        UUID id = UUID.randomUUID();
        StockEntity entity = new StockEntity(id, productId, stockQuantity);
        stockRepository.save(entity);
        return StockMapper.toDomain(entity);
    }
    
    @Transactional
    public Stock updateStock(UUID productId, int stockQuantity) {
        StockEntity entity = getStockEntity(productId);
        entity.updateQuantity(stockQuantity);
        stockRepository.save(entity);
        return StockMapper.toDomain(entity);
    }

    @Transactional(readOnly = true)
    public Stock getStock(UUID productId) {
        StockEntity entity = getStockEntity(productId);
        return StockMapper.toDomain(entity);
    }

    @Transactional
    public Stock deleteStock(UUID productId) {
        StockEntity entity = stockRepository.deleteByProductId(productId);
        return StockMapper.toDomain(entity);
    }

    @Transactional
    public Boolean decreaseStocks(Map<UUID, Integer> requestedQuantityMap) {
        StockEntity entity;
        for (UUID productId: requestedQuantityMap.keySet()) {
            entity = getStockEntity(productId);
            entity.decreaseQuantity(requestedQuantityMap.get(productId));
            
            // TODO: 동시성 제어 구현 (낙관적 락 및 재확인 구현)
            stockRepository.save(entity);
        }
        return true;
    }

    @Transactional
    public void decreaseStocksWithOptimisticLock(Map<UUID, Integer> requestedQuantityMap) {
        OptimisticStockEntity entity;
        for (UUID productId: requestedQuantityMap.keySet()) {
            Optional<OptimisticStockEntity> stockEntity = optimisticStockRepository.findByProductId(productId);
        
            if (stockEntity.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }
            entity = stockEntity.get(); 
            entity.decreaseQuantity(requestedQuantityMap.get(productId));
            
            optimisticStockRepository.save(entity);
            
        }
    }

    @Transactional
    public Boolean increaseStocks(Map<UUID, Integer> requestedQuantityMap) {
        StockEntity entity;
        for (UUID productId: requestedQuantityMap.keySet()) {
            entity = getStockEntity(productId);
            entity.increaseQuantity(requestedQuantityMap.get(productId));
            stockRepository.save(entity);
        }
        return true;
    }

    @Transactional
    public void increaseStocksWithOptimisticLock(Map<UUID, Integer> requestedQuantityMap) {
        OptimisticStockEntity entity;
        for (UUID productId: requestedQuantityMap.keySet()) {
            Optional<OptimisticStockEntity> stockEntity = optimisticStockRepository.findByProductId(productId);
            entity = stockEntity.get();
            entity.increaseQuantity(requestedQuantityMap.get(productId));
            optimisticStockRepository.save(entity);
        }
    }
}
