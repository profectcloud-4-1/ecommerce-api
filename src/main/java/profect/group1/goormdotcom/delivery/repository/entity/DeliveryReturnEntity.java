package profect.group1.goormdotcom.delivery.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import profect.group1.goormdotcom.common.domain.BaseEntity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Comment;
import jakarta.persistence.Column;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor


@Entity
@Table(name = "p_delivery_return")
@Comment("반송")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_delivery_return set deleted_at = NOW() where id = ?")
public class DeliveryReturnEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "delivery_id", nullable = false)
	@Comment("배송 ID (p_delivery.id)")
	private UUID deliveryId;

	@Column(name = "status", nullable = false, length = 7)
	@Comment("반송 상태 (code_key: DELIVERY_RETURN_STATUS)")
	private String status;

	@Column(name = "tracking_number")
	@Comment("송장 번호")
	private String trackingNumber;
	
}