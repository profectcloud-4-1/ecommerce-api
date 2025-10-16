package profect.group1.goormdotcom.brand.repository;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import java.util.Set;
import java.util.List;
import profect.group1.goormdotcom.brand.repository.entity.BrandEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import profect.group1.goormdotcom.brand.repository.BrandJpaRepository;

@Repository
@RequiredArgsConstructor
public class BrandRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
        "createdAt", "name"
    );

    private static final Set<String> ALLOWED_SEARCH_FIELDS = Set.of(
        "name"
    );

    private static final Set<String> ALLOWED_FILTER_FIELDS = Set.of(
        
    );

    public List<BrandEntity> searchBrands(String sort, String order, String searchField, String keyword, int page, int size, List<List<String>> filter) {
        String sortField = (sort != null && ALLOWED_SORT_FIELDS.contains(sort)) ? sort : "createdAt";
        boolean isAsc = "asc".equalsIgnoreCase(order);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BrandEntity> cq = cb.createQuery(BrandEntity.class);
        Root<BrandEntity> root = cq.from(BrandEntity.class);

        Predicate where = cb.conjunction();
        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasSearchField = searchField != null && ALLOWED_SEARCH_FIELDS.contains(searchField);

        if (hasKeyword && hasSearchField) {
            where = cb.and(where, cb.like(cb.lower(root.get(searchField).as(String.class)), "%" + keyword.toLowerCase() + "%"));
        }
        if (filter != null && !filter.isEmpty()) {
            for (List<String> filterItem : filter) {
                String filterField = filterItem.get(0);
                if (!ALLOWED_FILTER_FIELDS.contains(filterField)) continue;

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

        List<BrandEntity> entities = query.getResultList();
        return entities;
    }
}