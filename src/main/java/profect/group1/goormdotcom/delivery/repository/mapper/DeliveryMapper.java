package profect.group1.goormdotcom.delivery.repository.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.delivery.domain.Delivery;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryEntity;	

@Component
public class DeliveryMapper {

	public static Delivery toDomain(final DeliveryEntity entity) {
		return Delivery.builder()
				.id(entity.getId())
				.orderId(entity.getOrderId())
				.status(entity.getStatus())
				.trackingNumber(entity.getTrackingNumber())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}

	public static DeliveryEntity toEntity(final Delivery delivery) {
		return DeliveryEntity.builder()
				.id(delivery.getId())
				.orderId(delivery.getOrderId())
				.status(delivery.getStatus())
				.trackingNumber(delivery.getTrackingNumber())
				.build();
	}
}
