package profect.group1.goormdotcom.delivery.controller.external.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.delivery.domain.DeliveryAddress;
import profect.group1.goormdotcom.delivery.controller.external.v1.dto.response.DeliveryResponseDto;
import profect.group1.goormdotcom.delivery.controller.external.v1.dto.request.CreateAddressRequestDto;


@Tag(name = "Delivery (external)", description = "배송 API (외부 클라이언트 노출용)")
public interface DeliveryApiDocs {

    @Operation(summary = "배송 정보 조회", description = "주문 ID를 기반으로 배송 정보를 조회합니다.")
    ApiResponse<DeliveryResponseDto> getDeliveryByOrder(@RequestParam UUID orderId);

    @Operation(summary = "구름닷컴 배송지 조회", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
    ApiResponse<DeliveryAddress> getGoormAddress();

    @Operation(summary = "구름닷컴 배송지 생성", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
    ApiResponse<DeliveryAddress> createGoormAddress(@RequestBody CreateAddressRequestDto body);

    @Operation(summary = "구름닷컴 배송지 수정", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
    ApiResponse<DeliveryAddress> updateGoormAddress(
        @RequestBody CreateAddressRequestDto body
    );
}
