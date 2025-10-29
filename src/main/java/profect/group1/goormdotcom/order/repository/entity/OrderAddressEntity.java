
package profect.group1.goormdotcom.order.repository.entity;

import profect.group1.goormdotcom.common.domain.BaseEntity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_order_address")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_order_address set deleted_at = NOW() where id = ?")
public class OrderAddressEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    // 주문ID
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "address_detail", nullable = false)
    private String addressDetail;
    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "delivery_memo", nullable = false)
    private String deliveryMemo; 

}
