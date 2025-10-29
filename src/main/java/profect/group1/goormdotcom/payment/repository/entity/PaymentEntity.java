package profect.group1.goormdotcom.payment.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import profect.group1.goormdotcom.common.domain.BaseEntity;
import profect.group1.goormdotcom.payment.domain.enums.PayType;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Entity
@Table(name = "p_payment")
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id", columnDefinition = "uuid", nullable = false)
    private UUID userId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "order_id", columnDefinition = "uuid", nullable = false)
    private UUID orderId;

    @Column(name = "order_number", length = 200, nullable = false)
    private String orderNumber;

    @Column(name = "order_name", length = 200, nullable = false)
    private String orderName;

    @Column(name = "pay_type", nullable = false, length = 32)
    private String payType;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "canceled_amount", nullable = false)
    private Long canceledAmount;

    @Column(name = "payment_key", length = 200, nullable = true)
    private String paymentKey;

    @Column(name = "approved_at", nullable = true)
    private LocalDateTime approvedAt;

    @Column(name = "canceled_at", nullable = true)
    private LocalDateTime canceledAt;

    public PaymentEntity(final UUID userId,
                         final UUID orderId,
                         final String payType,
                         final Long amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.payType = payType;
        this.amount = amount;
        this.status = "PENDING"; //DB에서 펜딩상태 긁어오기
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCanceledAmount(Long canceledAmount) {
        this.canceledAmount = canceledAmount;
    }

    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public void setCanceledAt(LocalDateTime canceledAt) { this.canceledAt = canceledAt; }
}
