package profect.group1.goormdotcom.delivery.controller.internal.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.delivery.service.DeliveryService;
import profect.group1.goormdotcom.delivery.domain.Delivery;
import profect.group1.goormdotcom.delivery.controller.internal.v1.dto.request.StartDeliveryRequestDto;
import profect.group1.goormdotcom.delivery.controller.internal.v1.dto.request.CancelDeliveryRequestDto;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import jakarta.validation.Valid;
import java.util.UUID;
import profect.group1.goormdotcom.delivery.controller.internal.v1.DeliveryInternalApiDocs;

@RestController
@RequestMapping("/internal/v1/delivery")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryInternalController implements DeliveryInternalApiDocs {

    private final DeliveryService service;

    @GetMapping("/check/cancellable")
	public ApiResponse<Integer> checkCancellable(@RequestParam UUID orderId) {
		Integer canReturn = this.service.canReturn(orderId);
		return ApiResponse.onSuccess(canReturn);
	}

	@PostMapping("/start")
	public ApiResponse<Delivery> startDelivery(@RequestBody @Valid StartDeliveryRequestDto body) {
		Delivery delivery = this.service.startDelivery(body.getOrderId(), body.getCustomerId(), body.getAddress(), body.getAddressDetail(), body.getZipcode(), body.getPhone(), body.getName(), body.getDeliveryMemo());
		return ApiResponse.onSuccess(delivery);
	}

    @PostMapping("/cancel")
	public ApiResponse<Object> cancelDelivery(@RequestBody @Valid CancelDeliveryRequestDto body) {
		try {
			this.service.cancel(body.getOrderId());
			return ApiResponse.onSuccess(null);
		} catch (Exception e) {
			String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
			String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
			switch (e.getMessage()) {
				case "Delivery not found":
					code = ErrorStatus._NOT_FOUND.getCode();
					message = "배송 정보를 찾을 수 없습니다.";
					break;
				case "Delivery cannot be cancelled":
					code = ErrorStatus._FORBIDDEN.getCode();
					message = "현재 취소 가능한 상태가 아닙니다.";
					break;
			}
			return ApiResponse.onFailure(String.valueOf(code), message, null);
		}
	}

    @PostMapping("/return")
	public ApiResponse<Object> returnDelivery(@RequestBody @Valid CancelDeliveryRequestDto body) {
		try {
			this.service.returnDelivery(body.getOrderId());
			return ApiResponse.onSuccess(null);
		} catch (Exception e) {
			String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
			String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
			switch (e.getMessage()) {
				case "Delivery not found":
					code = ErrorStatus._NOT_FOUND.getCode();
					message = "배송 정보를 찾을 수 없습니다.";
					break;
				case "Delivery cannot be returned":
					code = ErrorStatus._FORBIDDEN.getCode();
					message = "현재 반송 가능한 상태가 아닙니다.";
					break;
			}
			return ApiResponse.onFailure(String.valueOf(code), message, null);
		}
	}

}