package profect.group1.goormdotcom.product.infrastructure.client.StockService.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record StockResponseDto(
    UUID productId,
    int stockQuantity,
    LocalDateTime updatedAt
) {
    
}
