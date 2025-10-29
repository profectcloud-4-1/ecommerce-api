package profect.group1.goormdotcom.order.controller.external.v1.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderItemDto {

    @NotNull
    private UUID productId;

    @NotNull
    // @Min(1)
    private int quantity;
}
