package profect.group1.goormdotcom.order.client.stock.dto;

import java.util.List;

public record StockAdjustmentRequestDto(
    List<StockAdjustmentRequestItemDto> products
) {
}