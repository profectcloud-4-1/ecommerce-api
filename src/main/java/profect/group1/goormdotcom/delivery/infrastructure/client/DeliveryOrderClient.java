package profect.group1.goormdotcom.delivery.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.UUID;
import java.lang.Boolean;
import org.springframework.web.bind.annotation.PathVariable;
import profect.group1.goormdotcom.apiPayload.ApiResponse;


@FeignClient(
		name = "delivery-to-order",
		fallback = DeliveryOrderClientFallBack.class
)
public interface DeliveryOrderClient {
	@PostMapping("/api/v1/orders/{orderId}/return-completed")
	ApiResponse<Boolean> onReturnCompleted(@PathVariable UUID orderId);
}
