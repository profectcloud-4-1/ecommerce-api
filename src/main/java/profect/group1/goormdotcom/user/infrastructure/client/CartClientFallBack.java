package profect.group1.goormdotcom.user.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.UUID;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
// import org.springframework.cloud.openfeign.Fallback;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;

@Slf4j
@Component
public class CartClientFallBack implements CartClient {


	@Override
	public ApiResponse<UUID> create(String authorization) {
		return ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), ErrorStatus._INTERNAL_SERVER_ERROR.getMessage(), null);
	}
}
