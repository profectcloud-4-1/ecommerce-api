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
@Table(name = "p_payment_history")
@EntityListeners(AuditingEntityListener.class)
public class PaymentHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "payment_id", columnDefinition = "uuid", nullable = false)
    private UUID paymentId;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "payment_key", length = 200, nullable = true)
    private String paymentKey;

    @Column(name = "raw_response", columnDefinition = "TEXT")
    private String rawResponse;

    public PaymentHistoryEntity(final UUID paymentId,
                         final String status,
                         final Long amount,
                                final String paymentKey,
                                final String rawResponse) {
        this.paymentId = paymentId;
        this.status = status;
        this.amount = amount;
        this.paymentKey = paymentKey;
        this.rawResponse = rawResponse;
    }
}
