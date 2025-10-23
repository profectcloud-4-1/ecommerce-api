package profect.group1.goormdotcom.order.repository.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import profect.group1.goormdotcom.order.repository.entity.CommonCodeEntity;


// 새로운 Erd 생성 할건지 말건지 기록용 History(order)
@Entity
@Table(name = "order_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderStatusEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private CommonCodeEntity status; // ORDER_STATUS 그룹의 코드

    @Column(nullable = false)
    private LocalDateTime createdAt; // 상태 전이 시각
}