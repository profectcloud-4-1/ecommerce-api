package profect.group1.goormdotcom.order.service;
import java.util.UUID;

public interface PaymentClinetService {
    void requestPayment(UUID orderId, int totalAmount);   // 선택: 주문 생성 시 결제 요청
    void cancelPayment(UUID orderId);                     // 취소 시 결제 취소
}
