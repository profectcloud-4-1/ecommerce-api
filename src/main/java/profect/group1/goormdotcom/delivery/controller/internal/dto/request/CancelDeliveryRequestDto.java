package profect.group1.goormdotcom.delivery.controller.internal.v1.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CancelDeliveryRequestDto {
    @Schema(description = "주문 ID (p_order.id)")
    private UUID orderId;
}