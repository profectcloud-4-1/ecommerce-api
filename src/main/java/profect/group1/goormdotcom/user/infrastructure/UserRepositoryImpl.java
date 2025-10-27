package profect.group1.goormdotcom.user.infrastructure;

import org.springframework.stereotype.Repository;
import profect.group1.goormdotcom.user.service.UserRepository;
import profect.group1.goormdotcom.user.domain.User;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.UUID;
import profect.group1.goormdotcom.user.infrastructure.UserJpaRepository;
import profect.group1.goormdotcom.user.infrastructure.converter.UserJpaConverter;
import profect.group1.goormdotcom.user.infrastructure.UserJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpaRepo;
    @PersistenceContext
    private EntityManager entityManager;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
        "createdAt", "name"
    );

    private static final Set<String> ALLOWED_SEARCH_FIELDS = Set.of(
        "email", "name"
    );

    @Override
    public Optional<User> findById(UUID id) {
        User user = jpaRepo.findById(id).map(UserJpaConverter::toDomain).orElse(null);
        return Optional.ofNullable(user);
    }

    @Override
    public User register(User user, String encryptedPassword) {
        UserJpaEntity entity = UserJpaConverter.toEntity(user);
        entity.setPassword(encryptedPassword);
        User registeredUser = UserJpaConverter.toDomain(jpaRepo.save(entity));

        return registeredUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepo.findByEmail(email).map(UserJpaConverter::toDomain);
    }

    @Override
    public User update(User user) {
        UserJpaEntity entity = UserJpaConverter.toEntity(user);
        return UserJpaConverter.toDomain(jpaRepo.save(entity));
    }

    @Override
    public Optional<String> getEncryptedPasswordByEmail(String email) {
        return jpaRepo.findByEmail(email).map(UserJpaEntity::getPassword);
    }

    @Override
    public List<User> findAll(String sort, String order, String searchField, String keyword, int page, int size, List<List<String>> filter) {
        String sortField = (sort != null && ALLOWED_SORT_FIELDS.contains(sort)) ? sort : "createdAt";
        boolean isAsc = "asc".equalsIgnoreCase(order);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserJpaEntity> cq = cb.createQuery(UserJpaEntity.class);
        Root<UserJpaEntity> root = cq.from(UserJpaEntity.class);

        Predicate where = cb.conjunction();
        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasSearchField = searchField != null && ALLOWED_SEARCH_FIELDS.contains(searchField);

        if (hasKeyword && hasSearchField) {
            where = cb.and(where, cb.like(cb.lower(root.get(searchField).as(String.class)), "%" + keyword.toLowerCase() + "%"));
        }
        if (filter != null && !filter.isEmpty()) {
            for (List<String> filterItem : filter) {
                String filterField = filterItem.get(0);
                String filterValue = filterItem.get(1);
                where = cb.and(where, cb.equal(root.get(filterField).as(String.class), filterValue));
            }
        }
        cq.where(where);

        cq.orderBy(
            isAsc? 
            cb.asc(root.get(sortField)) : 
            cb.desc(root.get(sortField))
        );
        

        var query = entityManager.createQuery(cq);
        int safePage = Math.max(1, page);
        int safeSize = (size == 10 || size == 30 || size == 50) ? size : 10;
        query.setFirstResult((safePage - 1) * safeSize);
        query.setMaxResults(safeSize);

        List<UserJpaEntity> entities = query.getResultList();
        return entities.stream().map(UserJpaConverter::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepo.deleteById(id);
    }

    @Override
    public void updateNameAndEmail(UUID userId, String name, String email) {
        if (name==null && email==null) return;
        
        UserJpaEntity entity = jpaRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (name != null) entity.setName(name);
        if (email != null) entity.setEmail(email);

        jpaRepo.save(entity);
    }

    @Override
    public void updateLastLoginAt(UUID userId) {
        jpaRepo.updateLastLoginAt(userId);
    }
}