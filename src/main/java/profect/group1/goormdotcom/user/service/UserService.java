package profect.group1.goormdotcom.user.service;

import profect.group1.goormdotcom.user.domain.User;
import profect.group1.goormdotcom.user.service.dto.CreateUserDto;
import profect.group1.goormdotcom.user.service.PasswordService;
import profect.group1.goormdotcom.common.security.JwtTokenProvider;
import profect.group1.goormdotcom.user.controller.dto.request.ListRequestDto;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.user.infrastructure.client.CartClient;
import java.util.UUID;
import profect.group1.goormdotcom.apiPayload.ApiResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final PasswordService passwordService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CartClient cartClient;

    public Optional<User> findById(UUID id) {
        return repo.findById(id);
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

        User user = User.builder()
            .name(body.getName())
            .email(body.getEmail())
            .role(body.getRole())
            .brandId(body.getBrandId())
            .build();
        user = repo.register(user, encryptedPassword);
        UUID userId = user.getId();

        String token = jwtTokenProvider.generateAccessToken(userId, user.getRole().name());
        ApiResponse<UUID> result = cartClient.create("Bearer " + token);
        UUID cartId =result.getResult();
        if (cartId == null)
            throw new IllegalStateException("Failed to create cart");
        

        return user;
    }

    public String login(String email, String password) {
        var userOpt = repo.findByEmail(email);
        if (userOpt.isEmpty()) throw new IllegalArgumentException("Invalid credentials");
        String encoded = repo.getEncryptedPasswordByEmail(email).orElse("");
        if (!passwordService.isMatch(password, encoded)) throw new IllegalArgumentException("Invalid credentials");

        User user = userOpt.get();
        repo.updateLastLoginAt(user.getId());
        String role = user.getRole() != null ? user.getRole().name() : null;
        return jwtTokenProvider.generateAccessToken(user.getId(), role);
    }

    public void edit(UUID userId, String name, String email) {
        User user = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (email != null && !email.equals(user.getEmail())) {
            if (isEmailExists(email)) throw new IllegalArgumentException("Email already exists");
        }
        repo.updateNameAndEmail(userId, name, email);
    }

    public void deleteById(UUID userId) {
        repo.deleteById(userId);
    }

    public List<User> findAllBy(ListRequestDto body) {
        List<List<String>> filter = null;
        if (body.getFilter() != null && !body.getFilter().isBlank()) {
            filter = Arrays.stream(body.getFilter().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(pair -> pair.split(":", 2))
                .filter(arr -> arr.length == 2 && !arr[0].isBlank())
                .map(arr -> List.of(arr[0].trim(), arr[1].trim()))
                .collect(Collectors.toList());
            if (filter.isEmpty()) {
                filter = null;
            }
        }
        return repo.findAll(
            body.getSort(), 
            body.getOrder(), 
            body.getSearchField(), 
            body.getKeyword(), 
            body.getPage(), 
            body.getSize(),
            filter
        );
    }
}
