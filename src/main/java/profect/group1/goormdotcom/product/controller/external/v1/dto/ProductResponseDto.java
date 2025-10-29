package profect.group1.goormdotcom.product.controller.external.v1.dto;

import java.util.List;
import java.util.UUID;

public record ProductResponseDto(
    String name,
    UUID brandId,
    UUID categoryId,
    String description,
    int price,
    List<String> imageIds
) {
    
}
