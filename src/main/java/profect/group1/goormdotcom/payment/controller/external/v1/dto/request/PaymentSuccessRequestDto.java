package profect.group1.goormdotcom.payment.controller.external.v1.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentSuccessRequestDto {
    private String orderId;
    private Long amount;
    private String paymentKey;
}
