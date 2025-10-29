package profect.group1.goormdotcom.stock.repository.mapper;

import profect.group1.goormdotcom.stock.domain.Stock;
import profect.group1.goormdotcom.stock.repository.entity.StockEntity;

public class StockMapper {
    public static Stock toDomain(StockEntity stockEntity) {
        return new Stock(
            stockEntity.getId(), 
            stockEntity.getProductId(), 
            stockEntity.getStockQuantity(),
            stockEntity.getCreatedAt(), 
            stockEntity.getUpdatedAt(), 
            stockEntity.getDeletedAt()
        );
    }

    public static StockEntity toEntity(Stock stock) {
        return new StockEntity(
            stock.getId(), 
            stock.getProductId(), 
            stock.getStockQuantity()
        );
    }
}
