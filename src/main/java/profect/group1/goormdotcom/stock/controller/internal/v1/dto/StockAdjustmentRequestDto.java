package profect.group1.goormdotcom.stock.controller.internal.v1.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequestDto(
    @NotNull List<ProductStockAdjustmentRequestDto> products
) {
}