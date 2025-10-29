package profect.group1.goormdotcom.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
/**
 * 배송 서비스와 통신하는 Feign Client
 * - 배송 요청
 * - 배송 상태 조회
 * - 배송 취소/반송 요청
 */
@FeignClient(name = "order-to-delivery")
public interface DeliveryClient {

    // /**
    //  * 배송 요청 (결제 완료 후)
    //  * @param request 배송 요청 정보
    //  * @return 배송 요청 성공 여부
    //  */
    // @PostMapping("/internal/v1/delivery")
    // Boolean requestDelivery(@RequestBody);

    // /**
    //  * 간단한 배송 요청 (orderId, customerId만)
    //  * @param orderId 주문 ID
    //  * @param customerId 고객 ID
    //  * @return 배송 요청 성공 여부
    //  */
    // @PostMapping("/internal/v1/delivery/request-simple")
    // Boolean requestDeliverySimple(@RequestParam UUID orderId, @RequestParam UUID customerId);

    /**
     * 배송 상태 조회
     * @param orderId 주문 ID
     * @return 배송 상태
     */
    @GetMapping("/internal/v1/delivery/status/{orderId}")
    DeliveryStatusResponse getDeliveryStatus(@PathVariable("orderId") UUID orderId);

    // @PostMapping("/internal/v1/delivery")
    // ApiResponse<UUID> createDelivery( @RequestBody CreateDeliveryRequest request);

    @PostMapping("/internal/v1/delivery/start")
    ApiResponse<UUID> startDelivery(@RequestBody StartDeliveryRequest request);
        /**
     * 배송 취소 요청 (배송 시작 전)
     * @param orderId 주문 ID
     * @return 취소 성공 여부
     */
    @PostMapping("/internal/v1/delivery/cancel")
    Boolean cancelDelivery(@RequestBody CancelDeliveryRequest request);

    /**
     * 반송 요청 (배송 완료 후 취소)
     * @param orderId 주문 ID
     * @return 반송 요청 성공 여부
     */
    @PostMapping("/internal/v1/delivery/return")
    Boolean requestReturn(@RequestBody ReturnDeliveryRequest request);

    // 취소가능여부 확인
    @GetMapping("/internal/v1/delivery/check/cancellable")
    ApiResponse<Integer> checkCancellable(@RequestParam("orderId") UUID orderId);

    /**
     * 배송 요청 DTO
     */
    record StartDeliveryRequest(
        UUID orderId,
        UUID customerId,
        String address,
        String addressDetail,
        String zipcode,
        String phone,
        String name,
        String deliveryMemo
    ) {}


    /**
     * 배송 상태 응답 DTO
     */
    record DeliveryStatusResponse(
        UUID orderId,
        DeliveryStatus status,
        String trackingNumber
    ) {}

    /**
     * 배송 상태 enum
     */
    enum DeliveryStatus {
        PENDING,      // 대기
        IN_DELIVERY,        // 배송 중
        FINISHED,      // 배송 완료, 반송완료료
        // RETURNED,       // 반송 완료
        CANCELLED       // 취소됨
    }

    record CancelDeliveryRequest(
        UUID orderId
    ) {}
    
    record ReturnDeliveryRequest(
        UUID orderId
    ) {}
}