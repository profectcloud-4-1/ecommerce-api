package profect.group1.goormdotcom.category.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.category.domain.CategoryNode;
import profect.group1.goormdotcom.category.domain.CategoryTree;
import profect.group1.goormdotcom.category.repository.CategoryRepository;
import profect.group1.goormdotcom.category.repository.entity.CategoryEntity;

@Service
@Transactional
@RequiredArgsConstructor
public class CategroyTreeService {
    private final CategoryRepository categoryRepository;

    private static final UUID ROOT_ID =
        UUID.fromString("00000000-0000-0000-0000-000000000000");

    private CategoryTree buildCategoryTree(CategoryEntity parent, List<CategoryEntity> entities) {

        // Build tree
        Map<UUID, CategoryNode> map = new LinkedHashMap<>(entities.size());
        for (CategoryEntity e : entities) {
            map.put(e.getId(), CategoryNode.of(e.getId(), e.getParentId(),e.getName()));
        }

        map.computeIfAbsent(parent.getId(), id -> CategoryNode.of(parent.getId(), null, parent.getName()));

        CategoryNode root = map.get(parent.getId());
        if (root == null) throw new IllegalStateException("Parent not found!");

        for (CategoryNode n : map.values()) {     
            if (n.id().equals(parent.getId())) continue;        

            CategoryNode p = map.get(n.parentId());
            p.children().add(n);
        } 

        CategoryTree categoryTree = new CategoryTree(root, map);
        return categoryTree;
    }

    public CategoryTree getAllCategoryTree() {
        CategoryEntity parent = new CategoryEntity(ROOT_ID, null, "ROOT");
        List<CategoryEntity> entities = categoryRepository.findAll();
        return buildCategoryTree(parent, entities);
    }

    public CategoryTree getChildCategoryTree(UUID parentId) {
        CategoryEntity parent = categoryRepository.findById(parentId)
            .orElseThrow(() ->  new IllegalArgumentException("Category not found"));
        List<CategoryEntity> entities = categoryRepository.findAllByParentId(parent.getId());
        return buildCategoryTree(parent, entities);
    }

}
