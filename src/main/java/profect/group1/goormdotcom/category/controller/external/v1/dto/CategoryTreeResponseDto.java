package profect.group1.goormdotcom.category.controller.external.v1.dto;

import java.util.List;
import java.util.UUID;

import profect.group1.goormdotcom.category.domain.CategoryNode;

public record CategoryTreeResponseDto(
    UUID id,
    String name,
    List<CategoryTreeResponseDto> children
) {
    public static CategoryTreeResponseDto from(CategoryNode node) {
        List<CategoryTreeResponseDto> childDtos = node.children().stream().map(CategoryTreeResponseDto::from).toList();
        return new CategoryTreeResponseDto(
            node.id(), 
            node.name(), 
            childDtos
        );
    }
}
