package profect.group1.goormdotcom.delivery.repository.mapper;

import profect.group1.goormdotcom.delivery.domain.DeliveryAddress;
import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.delivery.repository.entity.DeliveryAddressEntity;
import profect.group1.goormdotcom.delivery.repository.entity.GoormAddressEntity;


@Component
public class DeliveryAddressMapper {

    public DeliveryAddress toDomainOfSender(final DeliveryAddressEntity entity) {
        return DeliveryAddress.builder()
            .id(entity.getId())
            .address(entity.getSenderAddress())
            .addressDetail(entity.getSenderAddressDetail())
            .zipcode(entity.getSenderZipcode())
            .phone(entity.getSenderPhone())
            .name(entity.getSenderName())
            .build();
    }

    public DeliveryAddress toDomainOfReceiver(final DeliveryAddressEntity entity) {
        return DeliveryAddress.builder()
            .id(entity.getId())
            .address(entity.getReceiverAddress())
            .addressDetail(entity.getReceiverAddressDetail())
            .zipcode(entity.getReceiverZipcode())
            .phone(entity.getReceiverPhone())
            .name(entity.getReceiverName())
            .deliveryMemo(entity.getDeliveryMemo())
            .build();
    }

    public DeliveryAddress toDomainFromGoormAddress(final GoormAddressEntity entity) {
        return DeliveryAddress.builder()
            .id(entity.getId())
            .address(entity.getAddress())
            .addressDetail(entity.getAddressDetail())
            .zipcode(entity.getZipcode())
            .phone(entity.getPhone())
            .name(entity.getName())
            .deliveryMemo(null)
            .build();
    }
}