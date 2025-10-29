package profect.group1.goormdotcom.category.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.category.domain.Category;
import profect.group1.goormdotcom.category.domain.CategoryTree;
import profect.group1.goormdotcom.category.repository.CategoryRepository;
import profect.group1.goormdotcom.category.repository.entity.CategoryEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public UUID createCategory(String name, UUID parentId) {
        UUID id = UUID.randomUUID();
        CategoryEntity categoryEntity = new CategoryEntity(
            id, parentId, name
        );
        categoryRepository.save(categoryEntity);
        return id;
    }

    public Category updateCategory(UUID id, UUID parentId, String name) {
        CategoryEntity entity = categoryRepository.findById(id)
            .orElseThrow(() -> { throw new IllegalArgumentException("Category not found");});

        CategoryEntity newEntity = new CategoryEntity(entity.getId(), parentId, name);
        categoryRepository.save(newEntity);

        return new Category(newEntity.getId(), newEntity.getParentId(), newEntity.getName());
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
