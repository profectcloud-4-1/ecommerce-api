package profect.group1.goormdotcom.payment.domain;

import lombok.*;
import profect.group1.goormdotcom.common.domain.BaseEntity;
import profect.group1.goormdotcom.payment.domain.enums.PayType;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentHistory {
    private UUID id;
    private UUID paymentId;
    private String status;
    private Long amount;
    private String paymentKey;
    private String rawResponse;
    private String eventType;

    public PaymentHistory(
            UUID paymentId,
            String status,
            Long amount,
            String paymentKey,
            String rawResponse,
            String eventType
    ) {
        this.paymentId = paymentId;
        this.status = status;
        this.amount = amount;
        this.paymentKey = paymentKey;
        this.rawResponse = rawResponse;
        this.eventType = eventType;
    }

    public static PaymentHistory create(UUID paymentId,
                                        String status,
                                        Long amount,
                                        String paymentKey,
                                        String rawResponse,
                                        String eventType) {
        return new PaymentHistory(paymentId, status, amount, paymentKey, rawResponse, eventType);
    }
}
