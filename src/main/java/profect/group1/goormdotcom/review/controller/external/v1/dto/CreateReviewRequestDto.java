package profect.group1.goormdotcom.review.controller.external.v1.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CreateReviewRequestDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private UUID productId;

    @NotNull(message = "별점은 필수입니다.")
    @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
    private int rating;

    @NotNull(message = "내용은 필수입니다.")
    @Size(min = 10, max = 500, message = "리뷰는 10자 이상 500자 이하로 작성해주세요.")
    private String content;

    private UUID fileId;


}
