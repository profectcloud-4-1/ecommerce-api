package profect.group1.goormdotcom.order.repository.entity;

import profect.group1.goormdotcom.common.domain.BaseEntity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderProductEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    // 주문 정보
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    //상품 정보
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private UUID productId;


    // private UUID productId;

    @Column(name = "order_name", nullable=false)
    private String productName;
    
    //단일 상품 수량  
    @Column(name="quantity", nullable=false)
    private int quantity;

    @Column(name="total_amount", nullable=false)
    private int totalAmount;

}
