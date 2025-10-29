package profect.group1.goormdotcom.product.controller.external.v1.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateProductRequestDto(
    @NotBlank(message = "상품명은 필수입니다.")
    String name,
    @NotNull(message = "브랜드 ID는 필수입니다. 회원가입시 발급받은 브랜드 ID를 입력해주세요.")
    UUID brandId,    
    @NotNull(message = "카테고리 ID는 필수입니다. 카테고리 카탈로그를 참고하여 카테고리 ID를 입력해주세요.")
    UUID categoryId,
    @Size(max = 1024, message = "상품 설명은 필수입니다.") 
    String description,
    @Positive(message = "가격은 양수여야 합니다.")
    int price,
    @NotNull @Size(min = 1, message = "상품 이미지는 1개 이상 필요합니다.") 
    List<UUID> imageIds
) {    
}
