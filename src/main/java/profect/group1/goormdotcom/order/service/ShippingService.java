package profect.group1.goormdotcom.order.service;

import java.util.UUID;

public interface ShippingService {

    //출고(발송 시작) 처리
    void markShipped(UUID orderId);

    //배송 완료 처리
    void markDelivered(UUID orderId);

    //반송 완료 처리
    void markReturned(UUID orderId);

    //현재 배송 상태 조회
    String getStatus(UUID orderId);
    
}
