package profect.group1.goormdotcom.delivery.controller.internal.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.delivery.controller.internal.v1.dto.request.CancelDeliveryRequestDto;
import profect.group1.goormdotcom.delivery.controller.internal.v1.dto.request.StartDeliveryRequestDto;
import profect.group1.goormdotcom.delivery.domain.Delivery;
import java.lang.Boolean;

@Tag(name = "Delivery (internal)", description = "배송 API (내부서비스간 통신용)")
public interface DeliveryInternalApiDocs {

    @Operation(summary = "반송 가능 여부 확인")
    ApiResponse<Integer> checkCancellable(@RequestParam UUID orderId);

    @Operation(summary = "배송 시작 요청")
    ApiResponse<Delivery> startDelivery(@RequestBody StartDeliveryRequestDto body);

    @Operation(summary = "배송 취소")
    ApiResponse<Object> cancelDelivery(@RequestBody CancelDeliveryRequestDto body);

    @Operation(summary = "반송 요청")
    ApiResponse<Object> returnDelivery(@RequestBody CancelDeliveryRequestDto body);

    
}
