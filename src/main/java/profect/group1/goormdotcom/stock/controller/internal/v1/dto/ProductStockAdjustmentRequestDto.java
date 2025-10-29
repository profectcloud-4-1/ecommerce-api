package profect.group1.goormdotcom.stock.controller.internal.v1.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductStockAdjustmentRequestDto(
    @NotNull UUID productId,
    @Positive int requestedStockQuantity
) {
}