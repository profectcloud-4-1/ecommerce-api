package profect.group1.goormdotcom.category.controller.external.v1.dto;

import java.util.UUID;

public record CategoryResponseDto(
    UUID id,
    UUID parentId,
    String name
) {
} 
