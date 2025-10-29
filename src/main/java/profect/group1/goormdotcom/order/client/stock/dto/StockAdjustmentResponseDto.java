package profect.group1.goormdotcom.order.client.stock.dto;

import java.util.List;
import java.util.UUID;

public record StockAdjustmentResponseDto(
    Boolean status,
    List<UUID> productId 
) {
    
}

