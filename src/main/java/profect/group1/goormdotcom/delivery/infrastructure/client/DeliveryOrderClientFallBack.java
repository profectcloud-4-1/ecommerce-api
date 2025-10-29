package profect.group1.goormdotcom.delivery.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.lang.Boolean;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
// import org.springframework.cloud.openfeign.Fallback;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;

@Slf4j
@Component
public class DeliveryOrderClientFallBack implements DeliveryOrderClient {

	@Override
	public ApiResponse<Boolean> onReturnCompleted(UUID orderId) {
		return ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), ErrorStatus._INTERNAL_SERVER_ERROR.getMessage(), null);
	}
}
