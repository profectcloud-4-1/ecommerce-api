package profect.group1.goormdotcom.review.controller.external.v1.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatedReviewRequestDto {
    @NotNull(message = "별점은 필수입니다.")
    @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
    private Integer rating;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    @Size(min = 10, max = 500, message = "리뷰는 10자 이상 500자 이하로 작성해주세요.")
    private String content;

    // Optional: 이미지 URL 변경 (null이면 기존 유지, 빈 문자열이면 삭제)
    @Pattern(
            regexp = "^(https?://.*)?$",
            message = "올바른 URL 형식이 아닙니다."
    )
    private String imageUrl;

}
