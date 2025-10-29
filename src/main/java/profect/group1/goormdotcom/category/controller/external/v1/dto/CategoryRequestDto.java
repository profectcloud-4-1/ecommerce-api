package profect.group1.goormdotcom.category.controller.external.v1.dto;

import java.util.UUID;

public record CategoryRequestDto(
    String name,
    UUID parentId
) {
} 
