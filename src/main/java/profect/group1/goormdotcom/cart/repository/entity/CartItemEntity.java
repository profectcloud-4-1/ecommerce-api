package profect.group1.goormdotcom.cart.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import profect.group1.goormdotcom.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Entity
@Table(name = "p_cart_item")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_cart_item set deleted_at = NOW(), cart_id = NULL where id = ?")
@EntityListeners(AuditingEntityListener.class)
public class CartItemEntity extends BaseEntity {

	@Id
	@UuidGenerator
	private UUID id;
	private UUID cartId;
	private UUID productId;
	private int quantity;
	private int price;

	private LocalDateTime deletedAt;

	public CartItemEntity(
			final UUID id,
			final UUID cartId,
			final UUID productId,
			final int quantity,
			final int price) {
		this.id = id;
		this.cartId = cartId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}
}
