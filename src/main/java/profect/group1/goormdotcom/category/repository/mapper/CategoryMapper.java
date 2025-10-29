package profect.group1.goormdotcom.category.repository.mapper;

import org.springframework.stereotype.Component;

import profect.group1.goormdotcom.category.domain.Category;
import profect.group1.goormdotcom.category.repository.entity.CategoryEntity;

@Component
public class CategoryMapper {
    public static Category toDomain(
        final CategoryEntity entity
    ) {
        return new Category(
            entity.getId(),
            entity.getParentId(),
            entity.getName(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }
}
