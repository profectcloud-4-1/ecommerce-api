package profect.group1.goormdotcom.delivery.repository.mapper;

import profect.group1.goormdotcom.delivery.domain.DeliveryStepHistory;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryStepHistoryEntity;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryStepHistoryMapper {

    public DeliveryStepHistory toDomain(final DeliveryStepHistoryEntity entity) {
        return DeliveryStepHistory.builder()
            .stepType(entity.getStepType())
            .createdAt(entity.getCreatedAt())
            .build();
    }
}