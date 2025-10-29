package profect.group1.goormdotcom.payment.repository;


import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import profect.group1.goormdotcom.payment.domain.enums.Status;
import profect.group1.goormdotcom.payment.repository.entity.PaymentEntity;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID>,
                                           JpaSpecificationExecutor<PaymentEntity> {
    Optional<PaymentEntity> findByOrderId(UUID orderId);

    Optional<PaymentEntity> findByOrderIdAndStatus(UUID orderId, String status);

    Optional<PaymentEntity> findByOrderNumber(String orderNumber);

    Optional<PaymentEntity> findByPaymentKey(String paymentKey);

    boolean existsByPaymentKey(String paymentKey);

    Slice<PaymentEntity> findAllByUserId(UUID userId, Pageable pageable);
}
