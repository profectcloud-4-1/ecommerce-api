package profect.group1.goormdotcom.payment.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import profect.group1.goormdotcom.payment.infrastructure.client.dto.PaymentResultDto;

import java.util.UUID;

@FeignClient(
        name = "payment-to-order",
        fallback = OrderClientFallBack.class
)
public interface OrderClient {
    @PostMapping("/api/v1/orders/{orderId}/payment")
    void notifyPaymentResult(
            @PathVariable UUID orderId,
            @RequestBody PaymentResultDto paymentResultDto
    );
}