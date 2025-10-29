package profect.group1.goormdotcom.category.controller.external.v1.mapper;

import org.springframework.stereotype.Component;

import profect.group1.goormdotcom.category.controller.external.v1.dto.CategoryResponseDto;
import profect.group1.goormdotcom.category.controller.external.v1.dto.CategoryTreeResponseDto;
import profect.group1.goormdotcom.category.domain.Category;
import profect.group1.goormdotcom.category.domain.CategoryNode;


@Component
public class CategoryDtoMapper {
    public static CategoryTreeResponseDto toCategoryTreeDto(CategoryNode categoryNode) {
        return CategoryTreeResponseDto.from(categoryNode);
    }
    
    public static CategoryResponseDto toCategoryDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getParentId(), category.getName());
    }
}
