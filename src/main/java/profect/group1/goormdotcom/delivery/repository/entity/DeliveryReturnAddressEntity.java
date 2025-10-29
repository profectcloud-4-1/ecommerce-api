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
@Table(name = "p_delivery_return_address")
@Comment("반송 송수신지")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_delivery_return_address set deleted_at = NOW() where id = ?")
public class DeliveryReturnAddressEntity extends BaseEntity {

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "delivery_return_id", nullable = false)
	@Comment("반송 ID (p_delivery_return.id)")
	private UUID deliveryReturnId;

	@Column(name = "sender_address", nullable = false)
	@Comment("송신자 주소")
	private String senderAddress;

	@Column(name = "sender_address_detail", nullable = false)
	@Comment("송신자 상세주소")
	private String senderAddressDetail;

	@Column(name = "sender_zipcode", nullable = false)
	@Comment("송신자 우편번호")
	private String senderZipcode;

	@Column(name = "sender_phone", nullable = false)
	@Comment("송신자 전화번호")
	private String senderPhone;

	@Column(name = "sender_name", nullable = false)
	@Comment("송신자 이름")
	private String senderName;

	@Column(name = "receiver_address", nullable = false)
	@Comment("수신자 주소")
	private String receiverAddress;

	@Column(name = "receiver_address_detail", nullable = false)
	@Comment("수신자 상세주소")
	private String receiverAddressDetail;

	@Column(name = "receiver_zipcode", nullable = false)
	@Comment("수신자 우편번호")
	private String receiverZipcode;

	@Column(name = "receiver_phone", nullable = false)
	@Comment("수신자 전화번호")
	private String receiverPhone;

	@Column(name = "receiver_name", nullable = false)
	@Comment("수신자 이름")
	private String receiverName;

	@Column(name = "delivery_memo")
	@Comment("배송 메모")
	private String deliveryMemo;
	
}