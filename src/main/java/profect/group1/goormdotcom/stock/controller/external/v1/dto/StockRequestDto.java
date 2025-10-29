package profect.group1.goormdotcom.stock.controller.external.v1.dto;

import java.util.UUID;

public record StockRequestDto(
    UUID productId,
    int stockQuantity
) {
}
