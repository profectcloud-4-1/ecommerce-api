package profect.group1.goormdotcom.cart.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import profect.group1.goormdotcom.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Entity
@Table(name = "p_cart")
@EntityListeners(AuditingEntityListener.class)
public class CartEntity extends BaseEntity {

	@Id
	@UuidGenerator
	private UUID id;
	private UUID customerId;

	public CartEntity(final UUID customerId) {
		this.customerId = customerId;
	}
}
