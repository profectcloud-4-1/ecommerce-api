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
public class StartDeliveryRequestDto {
    @Schema(description = "주문 ID (p_order.id)")
    private UUID orderId;
    @Schema(description = "구매자 ID (p_customer.id)")
    private UUID customerId;

    @Schema(description = "배송지 주소")
    private String address;
    @Schema(description = "배송지 상세주소")
    private String addressDetail;
    @Schema(description = "배송지 우편번호")
    private String zipcode;
    @Schema(description = "배송지 전화번호")
    private String phone;
    @Schema(description = "수취인 이름")
    private String name;
    @Schema(description = "배송 메모")
    private String deliveryMemo;
}