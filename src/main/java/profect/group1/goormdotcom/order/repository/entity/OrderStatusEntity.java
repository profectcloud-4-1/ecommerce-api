package profect.group1.goormdotcom.order.repository.entity;

import profect.group1.goormdotcom.common.domain.BaseEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;


// 새로운 Erd 생성 할건지 말건지 기록용 History(order)
@Entity
@Table(name = "p_order_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderStatusEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "status", nullable = false)
    private String status; // ORDER_STATUS 그룹의 코드

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

}