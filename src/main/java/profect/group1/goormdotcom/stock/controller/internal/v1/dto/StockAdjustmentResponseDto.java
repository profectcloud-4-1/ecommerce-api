package profect.group1.goormdotcom.stock.controller.internal.v1.dto;

import java.util.List;
import java.util.UUID;

public record StockAdjustmentResponseDto(
    Boolean status,
    List<UUID> productId 
) {
    
}
