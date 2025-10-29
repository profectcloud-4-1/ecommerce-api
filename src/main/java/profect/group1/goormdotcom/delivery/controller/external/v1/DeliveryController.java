package profect.group1.goormdotcom.delivery.controller.external.v1;

import jakarta.validation.Valid;
import java.util.UUID;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import profect.group1.goormdotcom.delivery.service.DeliveryService;
import profect.group1.goormdotcom.delivery.domain.Delivery;
import profect.group1.goormdotcom.delivery.domain.DeliveryAddress;
import profect.group1.goormdotcom.delivery.controller.external.v1.dto.response.DeliveryResponseDto;
import profect.group1.goormdotcom.delivery.controller.external.v1.dto.request.CreateAddressRequestDto;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryController implements DeliveryApiDocs {

	private final DeliveryService service;

	@GetMapping("/by-order")
	public ApiResponse<DeliveryResponseDto> getDeliveryByOrder(@RequestParam UUID orderId) {
		Delivery delivery = this.service.getDeliveryByOrderId(orderId);
		DeliveryResponseDto response = new DeliveryResponseDto(delivery);
		return ApiResponse.onSuccess(response);
	}


	

	// ===== Goorm Address (MASTER 전용) =====
    @GetMapping("/address/goorm")
	@PreAuthorize("hasRole('MASTER')")
    public ApiResponse<DeliveryAddress> getGoormAddress() {
		try {
        return ApiResponse.onSuccess(this.service.getGoormAddress());
		} catch (Exception e) {
			String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            switch (e.getMessage()) {
                case "Goorm address not found":
                    code = ErrorStatus._NOT_FOUND.getCode();
                    message = "등록된 배송지가 없습니다.";
                    break;
            }
            return ApiResponse.onFailure(String.valueOf(code), message, null);
		}
	}

	@PostMapping("/address/goorm")
	@PreAuthorize("hasRole('MASTER')")
    public ApiResponse<DeliveryAddress> createGoormAddress(
        @RequestBody @Valid CreateAddressRequestDto body
    ) {
		try {
        DeliveryAddress address = this.service.createGoormAddress(body);
		return ApiResponse.onSuccess(address);
		} catch (Exception e) {
			String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            switch (e.getMessage()) {
                case "Goorm address already exists":
                    code = ErrorStatus._CONFLICT.getCode();
                    message = ErrorStatus._CONFLICT.getMessage();
                    break;
            }
            return ApiResponse.onFailure(String.valueOf(code), message, null);
		}
	}

	@PutMapping("/address/goorm")
	@PreAuthorize("hasRole('MASTER')")
	public ApiResponse<DeliveryAddress> updateGoormAddress(
		@RequestBody @Valid CreateAddressRequestDto body
	) {
		DeliveryAddress address = this.service.updateGoormAddress(body);
		return ApiResponse.onSuccess(address);
	}
}
