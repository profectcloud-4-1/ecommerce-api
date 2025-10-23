package profect.group1.goormdotcom.order.controller.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID sellerId;

    @NotNull
    private UUID productId;

    @Min(1)
    private int quantity;

    @Min(0)
    private int totalAmount;

    private String orderName;
}
