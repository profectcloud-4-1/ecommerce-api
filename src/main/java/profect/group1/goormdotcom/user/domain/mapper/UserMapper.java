package profect.group1.goormdotcom.user.domain.mapper;

import profect.group1.goormdotcom.user.domain.User;
import profect.group1.goormdotcom.user.repository.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User toDomain(UserEntity entity) {
        return User.builder()
            .id(entity.getId())
            .createdAt(entity.getCreatedAt())
            .email(entity.getEmail())
            .name(entity.getName())
            .role(entity.getRole())
            .build();
    }

    public static UserEntity toEntity(User domain) {
        return UserEntity.builder()
            .id(domain.getId()) 
            .email(domain.getEmail())
            .name(domain.getName())
            .role(domain.getRole())
            .build();
    }
}


