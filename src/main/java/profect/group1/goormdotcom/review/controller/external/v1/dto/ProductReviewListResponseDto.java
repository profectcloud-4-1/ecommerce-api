package profect.group1.goormdotcom.review.controller.external.v1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * 상품별 리뷰 목록 응답 DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewListResponseDto {
    private UUID productId;
    private Double averageRating;    // 평균 별점
    private Integer totalReviews;    // 총 리뷰 개수
    private List<ReviewResponseDto> reviews;  // 리뷰 목록

    // 페이징 정보
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
