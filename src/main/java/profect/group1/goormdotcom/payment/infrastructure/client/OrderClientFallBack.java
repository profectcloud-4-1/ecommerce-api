package profect.group1.goormdotcom.payment.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.payment.infrastructure.client.dto.PaymentResultDto;

import java.util.UUID;

@Slf4j
@Component
public class OrderClientFallBack implements OrderClient {

    @Override
    public void notifyPaymentResult(UUID orderId, PaymentResultDto dto) {
        log.error("[Feign-Fallback] order-service 호출 실패: orderId={}, dto={}", orderId, dto);
        // TODO: MSA 분리 시 아웃박스 추가
    }
}