package profect.group1.goormdotcom.user.infrastructure.converter;

import profect.group1.goormdotcom.user.domain.User;
import profect.group1.goormdotcom.user.infrastructure.UserJpaEntity;

public class UserJpaConverter {
    public static User toDomain(UserJpaEntity jpa) {
        return User.builder()
            .id(jpa.getId())
            .createdAt(jpa.getCreatedAt())
            .email(jpa.getEmail())
            .name(jpa.getName())
            .role(jpa.getRole())
            .brandId(jpa.getBrandId())
            .build();
    }

    public static UserJpaEntity toEntity(User domain) {
        return UserJpaEntity.builder()
            .id(domain.getId()) 
            .createdAt(domain.getCreatedAt())
            .email(domain.getEmail())
            .name(domain.getName())
            .role(domain.getRole())
            .brandId(domain.getBrandId())
            .build();
    }
}


