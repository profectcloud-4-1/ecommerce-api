package profect.group1.goormdotcom.product.infrastructure.client.StockService.dto;

import java.util.UUID;

public record StockRequestDto(
    UUID productId,
    int stockQuantity
) {
    
}
