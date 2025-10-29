package profect.group1.goormdotcom.payment.repository.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.cart.domain.Cart;
import profect.group1.goormdotcom.payment.domain.Payment;
import profect.group1.goormdotcom.payment.domain.enums.Status;
import profect.group1.goormdotcom.payment.repository.entity.PaymentEntity;

@Component
public class PaymentMapper {
    public static Payment toDomain(
            final PaymentEntity entity
    ) {
        return new Payment(
                entity.getId(),
                entity.getUserId(),
                entity.getOrderId(),
                entity.getOrderNumber(),
                entity.getOrderName(),
                entity.getPayType(),
                entity.getStatus(),
                entity.getAmount(),
                entity.getCanceledAmount(),
                entity.getPaymentKey(),
                entity.getApprovedAt(),
                entity.getCanceledAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static PaymentEntity toEntity(Payment payment) {
        return new PaymentEntity(
                payment.getId(),
                payment.getUserId(),
                payment.getOrderId(),
                payment.getOrderNumber(),
                payment.getOrderName(),
                payment.getPayType(),
                payment.getStatus(),
                payment.getAmount(),
                payment.getCanceledAmount(),
                payment.getPaymentKey(),
                payment.getApprovedAt(),
                payment.getCanceledAt()
        );
    }

}
