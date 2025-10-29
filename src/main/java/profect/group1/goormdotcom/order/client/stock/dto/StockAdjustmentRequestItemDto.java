package profect.group1.goormdotcom.order.client.stock.dto;

import java.util.UUID;

public record StockAdjustmentRequestItemDto(
    UUID productId,
    int requestedStockQuantity
) {
}