package profect.group1.goormdotcom.delivery.repository.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.delivery.domain.DeliveryReturn;	
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryReturnEntity;

@Component
public class DeliveryReturnMapper {

	public static DeliveryReturn toDomain(final DeliveryReturnEntity entity) {
		return DeliveryReturn.builder()
				.id(entity.getId())
				.deliveryId(entity.getDeliveryId())
				.status(entity.getStatus())
				.trackingNumber(entity.getTrackingNumber())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}	

	public static DeliveryReturnEntity toEntity(final DeliveryReturn deliveryReturn) {
		return DeliveryReturnEntity.builder()
				.id(deliveryReturn.getId())
				.deliveryId(deliveryReturn.getDeliveryId())
				.status(deliveryReturn.getStatus())
				.trackingNumber(deliveryReturn.getTrackingNumber())
				.build();
	}
}
