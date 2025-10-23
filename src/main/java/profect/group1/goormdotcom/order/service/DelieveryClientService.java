package profect.group1.goormdotcom.order.service;
import java.util.UUID;
public interface DelieveryClientService {
    
    enum DeliveryState {
        BEFORE_SHIPMENT,   // 발송 전
        IN_TRANSIT,        // 배송 중
        DELIVERED,         // 배송 완료
        RETURNED,          // 반송 완료
        UNKNOWN
    }

    // DeliveryState getState(UUID orderId);
    // void requestShipment(UUID orderId);      // 선택: 주문 생성 후 배송 요청
    // void requestReturn(UUID orderId);        /// 배송 완료 후 취소 → 반송 요청
}


