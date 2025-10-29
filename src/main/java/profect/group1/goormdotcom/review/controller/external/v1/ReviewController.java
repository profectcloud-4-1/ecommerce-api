package profect.group1.goormdotcom.review.controller.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profect.group1.goormdotcom.review.controller.external.v1.dto.CreateReviewRequestDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.ProductReviewListResponseDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.ReviewResponseDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.UpdatedReviewRequestDto;
import profect.group1.goormdotcom.review.service.ReviewService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     * POST /api/v1/reviews
     */
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @Valid @RequestBody CreateReviewRequestDto request,
            @RequestHeader("X-User-Id") UUID userId  // 임시: 실제론 Security Context에서
    ) {
        ReviewResponseDto response = reviewService.createReview(request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 리뷰 단건 조회
     * GET /api/v1/reviews/{reviewId}
     */
    /*
    @GetMapping("/{reviewId}")

    public ResponseEntity<ReviewResponseDto> getReview(
            @PathVariable UUID reviewId
    ) {
        ReviewResponseDto response = reviewService.getReview(reviewId);
        return ResponseEntity.ok(response);
    }*/

    /**
     * 리뷰 수정
     * PUT /api/v1/reviews/{reviewId}
     */
    @PutMapping("/{reviewId}")

    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable UUID reviewId,
            @Valid @RequestBody UpdatedReviewRequestDto request,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        // TODO: Service 구현 후 연결
        // 임시: userId 없으면 더미 값 사용
        if (userId == null) {
            userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        }

        ReviewResponseDto response = reviewService.updateReview(reviewId, request, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 삭제
     * DELETE /api/v1/reviews/{reviewId}
     */
    @DeleteMapping("/{reviewId}")

    public ResponseEntity<Void> deleteReview(
            @PathVariable UUID reviewId,
            @RequestHeader(value = "X-User-Id", required = false) UUID userId
    ) {
        // TODO: Service 구현 후 연결
        // 임시: userId 없으면 더미 값 사용
        if (userId == null) {
            userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        }

        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 상품별 리뷰 목록 조회
     * GET /api/v1/reviews/products/{productId}
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductReviewListResponseDto> getProductReviews(
            @PathVariable UUID productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        // 서비스 호출
        ProductReviewListResponseDto response = reviewService.getProductReviews(
                productId, page, size, sortBy
        );

        // ResponseEntity에 DTO 담아서 반환
        return ResponseEntity.ok(response);
    }
}



