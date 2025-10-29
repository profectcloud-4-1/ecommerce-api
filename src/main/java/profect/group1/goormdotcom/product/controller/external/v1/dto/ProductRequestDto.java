package profect.group1.goormdotcom.product.controller.external.v1.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(
    @NotBlank(message = "상품명은 필수입니다.")
    String name,

    // NOTE: 브랜드 입점 구조가 아니지만 컬럼을 없애기엔 건드릴 부분이 많을 듯해서 validation만 제거했습니다. (221028 김현우)
    UUID brandId,    
    @NotNull(message = "카테고리 ID는 필수입니다. 카테고리 카탈로그를 참고하여 카테고리 ID를 입력해주세요.")
    UUID categoryId,
    @Size(max = 1024, message = "상품 설명은 필수입니다.") 
    String description,
    @Positive(message = "가격은 양수여야 합니다.")
    int price,
    @Positive(message = "재고수량은 양수여야 합니다.")
    int stockQuantity,
    @NotNull @Size(min = 1, message = "상품 이미지는 1개 이상 필요합니다.") 
    List<UUID> imageIds
) {    
}
