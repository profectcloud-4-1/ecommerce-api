package profect.group1.goormdotcom.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.payment.repository.entity.PaymentHistoryEntity;

import java.util.UUID;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, UUID> {

}