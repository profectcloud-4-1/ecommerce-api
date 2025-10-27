package profect.group1.goormdotcom.user.service;

import profect.group1.goormdotcom.user.domain.User;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    User register(User user, String encryptedPassword);
    User update(User user);
    Optional<User> findByEmail(String email);
    Optional<String> getEncryptedPasswordByEmail(String email);
    List<User> findAll(String sort, String order, String searchField, String keyword, int page, int size, List<List<String>> filter);
    void deleteById(UUID id);
    void updateNameAndEmail(UUID userId, String name, String email);
    void updateLastLoginAt(UUID userId);
}