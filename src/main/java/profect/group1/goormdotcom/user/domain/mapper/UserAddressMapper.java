package profect.group1.goormdotcom.user.domain.mapper;

import profect.group1.goormdotcom.user.domain.UserAddress;
import profect.group1.goormdotcom.user.repository.entity.UserAddressEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAddressMapper {
    public static UserAddress toDomain(UserAddressEntity entity) {
        return UserAddress.builder()
            .id(entity.getId())
            .createdAt(entity.getCreatedAt())
            .userId(entity.getUserId())
            .address(entity.getAddress())
            .addressDetail(entity.getAddressDetail())
            .zipcode(entity.getZipcode())
            .phone(entity.getPhone())
            .name(entity.getName())
            .deliveryMemo(entity.getDeliveryMemo())
            .build();
    }

    public static UserAddressEntity toEntity(UserAddress domain) {
        return UserAddressEntity.builder()
            .id(domain.getId())
            .userId(domain.getUserId())
            .address(domain.getAddress())
            .addressDetail(domain.getAddressDetail())
            .zipcode(domain.getZipcode())
            .phone(domain.getPhone())
            .name(domain.getName())
            .deliveryMemo(domain.getDeliveryMemo())
            .build();
    }
}