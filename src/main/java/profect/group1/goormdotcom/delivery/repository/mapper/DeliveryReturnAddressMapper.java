package profect.group1.goormdotcom.delivery.repository.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryReturnAddressEntity;
import profect.group1.goormdotcom.delivery.domain.DeliveryAddress;


@Component
public class DeliveryReturnAddressMapper {

    public DeliveryAddress toDomainOfSender(final DeliveryReturnAddressEntity entity) {
        return DeliveryAddress.builder()
            .address(entity.getSenderAddress())
            .addressDetail(entity.getSenderAddressDetail())
            .zipcode(entity.getSenderZipcode())
            .phone(entity.getSenderPhone())
            .name(entity.getSenderName())
            .build();
    }

    public DeliveryAddress toDomainOfReceiver(final DeliveryReturnAddressEntity entity) {
        return DeliveryAddress.builder()
            .address(entity.getReceiverAddress())
            .addressDetail(entity.getReceiverAddressDetail())
            .zipcode(entity.getReceiverZipcode())
            .phone(entity.getReceiverPhone())
            .name(entity.getReceiverName())
            .build();
    }
}