package profect.group1.goormdotcom.payment.repository.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.payment.domain.PaymentHistory;
import profect.group1.goormdotcom.payment.repository.entity.PaymentHistoryEntity;

@Component
public class PaymentHistoryMapper {
    public static PaymentHistoryEntity toEntity(PaymentHistory history) {
        return new PaymentHistoryEntity(
                history.getPaymentId(),
                history.getStatus(),
                history.getAmount(),
                history.getPaymentKey(),
                history.getRawResponse()
        );
    }
}
