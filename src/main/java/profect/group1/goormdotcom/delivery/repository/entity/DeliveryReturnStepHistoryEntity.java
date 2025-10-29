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
@Table(name = "p_delivery_return_step_history")
@Comment("반송 단계 이력")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_delivery_return_step_history set deleted_at = NOW() where id = ?")
public class DeliveryReturnStepHistoryEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "delivery_return_id", nullable = false)
	@Comment("반송 ID (p_delivery_return.id)")
	private UUID deliveryReturnId;

	@Column(name = "step_type", nullable = false, length = 7)
	@Comment("반송 단계 (code_key: DELIVERY_RETURN_STEP_TYPE)")
	private String stepType;
	
}