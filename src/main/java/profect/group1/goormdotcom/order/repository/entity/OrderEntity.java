package profect.group1.goormdotcom.order.repository.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "p_order") // 실제 DB 테이블명
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// @EntityListeners(AuditingEntityListener.class)
@Builder(toBuilder = true)
public class OrderEntity {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    
    private UUID customerId;
    private UUID sellerId;

    // 재고와 연결
    private UUID productId;
    @Column(name = "stock_quantity", nullable = false)
    private int quantity;

    private int totalAmount;
    private String orderName;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
    // private LocalDateTime orderDate;

    // @Enumerated(EnumType.STRING)
    // private OrderStatus orderStatus;

}
