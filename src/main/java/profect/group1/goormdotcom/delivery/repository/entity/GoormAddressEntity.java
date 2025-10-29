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
import profect.group1.goormdotcom.common.domain.BaseEntity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Comment;
import jakarta.persistence.Column;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString

@Entity
@Table(name = "p_goorm_address")
@Comment("구름닷컴 배송지")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_goorm_address set deleted_at = NOW() where id = ?")
public class GoormAddressEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "address", nullable = false)
	@Comment("주소")
	private String address;
	@Column(name = "address_detail", nullable = false)
	@Comment("상세주소")
	private String addressDetail;
	@Column(name = "zipcode", nullable = false)
	@Comment("우편번호")
	private String zipcode;
	@Column(name = "phone", nullable = false)
	@Comment("전화번호")
	private String phone;
	@Column(name = "name", nullable = false)
	@Comment("수취인 이름")
	private String name;

	
}
