package profect.group1.goormdotcom.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.stock.repository.StockRepository;
import profect.group1.goormdotcom.stock.repository.entity.StockEntity;

@Getter
@Service
@RequiredArgsConstructor
public class AdjustStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void tryDecreaseQuantity(StockEntity entity, int requestedStockQuantity) {
        entity.decreaseQuantity(requestedStockQuantity);
        stockRepository.save(entity);
    }

    @Transactional
    public void tryIncreaseQuantity(StockEntity entity, int requestedStockQuantity) {
        entity.increaseQuantity(requestedStockQuantity);
        stockRepository.save(entity);
    }
}