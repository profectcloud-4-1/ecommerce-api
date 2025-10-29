package profect.group1.goormdotcom.user.service;

import java.util.UUID;
import java.util.List;
import profect.group1.goormdotcom.user.domain.UserAddress;
import profect.group1.goormdotcom.user.controller.external.v1.dto.request.UserAddressRequestDto;
import profect.group1.goormdotcom.user.repository.UserAddressRepository;
import profect.group1.goormdotcom.user.domain.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import profect.group1.goormdotcom.user.repository.entity.UserAddressEntity;

@Service
@RequiredArgsConstructor
public class UserAddressService {
    private final UserAddressRepository repo;
    private final UserAddressMapper mapper;

    public UserAddress createUserAddress(UUID userId, UserAddressRequestDto userAddressRequestDto) {
        UserAddressEntity entity = UserAddressEntity.builder()
            .userId(userId)
            .address(userAddressRequestDto.getAddress())
            .addressDetail(userAddressRequestDto.getAddressDetail())
            .zipcode(userAddressRequestDto.getZipcode())
            .phone(userAddressRequestDto.getPhone())
            .name(userAddressRequestDto.getName())
            .deliveryMemo(userAddressRequestDto.getDeliveryMemo())
            .build();
        entity = repo.save(entity);
        return mapper.toDomain(entity);
    }

    public UserAddress updateUserAddress(UUID addressId, UserAddressRequestDto userAddressRequestDto) {
        UserAddressEntity entity = repo.findById(addressId)
            .orElseThrow(() -> new IllegalArgumentException("UserAddress not found"));

        entity.setAddress(userAddressRequestDto.getAddress());
        entity.setAddressDetail(userAddressRequestDto.getAddressDetail());
        entity.setZipcode(userAddressRequestDto.getZipcode());
        entity.setPhone(userAddressRequestDto.getPhone());
        entity.setName(userAddressRequestDto.getName());
        entity.setDeliveryMemo(userAddressRequestDto.getDeliveryMemo());
        entity = repo.save(entity);

        return mapper.toDomain(entity);
    }

    public boolean deleteUserAddress(UUID addressId) {
        UserAddressEntity entity = repo.findById(addressId)
            .orElseThrow(() -> new IllegalArgumentException("UserAddress not found"));
        repo.delete(entity);
        return true;
    }

    public UserAddress getById(UUID addressId) {
        UserAddressEntity entity = repo.findById(addressId)
            .orElseThrow(() -> new IllegalArgumentException("UserAddress not found"));
        return mapper.toDomain(entity);
    }

    public List<UserAddress> getAllByUserId(UUID userId) {
        return repo.findAllByUserId(userId).stream().map(UserAddressMapper::toDomain).collect(Collectors.toList());
    }
}