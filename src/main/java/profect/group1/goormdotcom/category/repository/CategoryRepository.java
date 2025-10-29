package profect.group1.goormdotcom.category.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import profect.group1.goormdotcom.category.repository.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID>{

    public List<CategoryEntity> findAllByParentId(UUID parentId);
}
