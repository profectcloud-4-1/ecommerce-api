package profect.group1.goormdotcom.user.service;

import profect.group1.goormdotcom.user.domain.User;
import profect.group1.goormdotcom.user.service.dto.CreateUserDto;
import profect.group1.goormdotcom.user.service.PasswordService;
import profect.group1.goormdotcom.common.security.JwtTokenProvider;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.user.infrastructure.client.CartClient;
import java.util.UUID;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.user.repository.UserRepository;
import profect.group1.goormdotcom.user.repository.entity.UserEntity;
import profect.group1.goormdotcom.user.domain.mapper.UserMapper;
import profect.group1.goormdotcom.user.domain.enums.UserRole;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final PasswordService passwordService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CartClient cartClient;
    private final UserMapper userMapper;

    public User findById(UUID id) {
        UserEntity entity = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return this.userMapper.toDomain(entity);
    }

    public boolean isEmailExists(String email) {
        boolean exists = repo.findByEmail(email).isPresent();
        return exists;
    }

    @Transactional
    public User register(CreateUserDto body) {
        if (!passwordService.validate(body.getPassword())) throw new IllegalArgumentException("Invalid password");
        String encryptedPassword = passwordService.encrypt(body.getPassword());

        if (isEmailExists(body.getEmail())) throw new IllegalArgumentException("Email already exists");

        String role = UserRole.CUSTOMER.getCode();

        UserEntity entity = UserEntity.builder()
            .name(body.getName())
            .role(role)
            .email(body.getEmail())
            .password(encryptedPassword)
            .build();
        entity = repo.save(entity);
        UUID userId = entity.getId();

        String token = jwtTokenProvider.generateAccessToken(userId, role);

        ApiResponse<UUID> result = cartClient.create("Bearer " + token);
        UUID cartId =result.getResult();
        if (cartId == null)
            throw new IllegalStateException("Failed to create cart");
        

        return this.userMapper.toDomain(entity);
    }

    public String login(String email, String password) {
        UserEntity entity = repo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        
        String encoded = entity.getPassword();
        if (!passwordService.isMatch(password, encoded)) throw new IllegalArgumentException("Invalid credentials");

        entity.setLastLoginAt(LocalDateTime.now());
        repo.save(entity);
        String role = entity.getRole();
        return jwtTokenProvider.generateAccessToken(entity.getId(), role);
    }

    public void edit(UUID userId, String name, String email) {
        UserEntity entity = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (email != null && !email.equals(entity.getEmail())) {
            if (isEmailExists(email)) throw new IllegalArgumentException("Email already exists");
        }
        entity.setName(name);
        entity.setEmail(email);
        repo.save(entity);
    }

    public void deleteById(UUID userId) {
        UserEntity entity = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        repo.delete(entity);
    }
}
