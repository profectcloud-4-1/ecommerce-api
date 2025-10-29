package profect.group1.goormdotcom.order.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import profect.group1.goormdotcom.order.repository.entity.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {}
    

