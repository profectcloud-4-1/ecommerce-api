package profect.group1.goormdotcom.review.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import profect.group1.goormdotcom.review.controller.dto.CreateReviewRequestDto;
import profect.group1.goormdotcom.review.controller.dto.ProductReviewListResponseDto;
import profect.group1.goormdotcom.review.controller.dto.ReviewResponseDto;
import profect.group1.goormdotcom.review.controller.dto.UpdatedReviewRequestDto;

import java.util.UUID;

public interface ReviewApiDocs {
    @Operation(
            summary = "리뷰 작성 API",
            description = "사용자가 상품에 대한 리뷰를 작성합니다. 이미 동일 주문에 대한 리뷰가 존재하면 400 에러를 반환합니다.",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponse(
            responseCode = "201",
            description = "리뷰 작성 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "success",
                            value = "{\"code\":\"COMMON201\",\"message\":\"리뷰 작성 성공\"}"
                    )
            )
    )
    ResponseEntity<ReviewResponseDto> createReview(
            @Parameter(description = "리뷰 작성 요청 데이터")
            @RequestBody CreateReviewRequestDto request,

            @Parameter(description = "유저 ID (X-User-Id 헤더)")
            @RequestHeader("X-User-Id") UUID userId
    );

    @Operation(
            summary = "리뷰 수정 API",
            description = "사용자가 작성한 리뷰를 수정합니다. 작성자 본인만 수정할 수 있습니다.",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponse(
            responseCode = "200",
            description = "리뷰 수정 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "success",
                            value = "{\"code\":\"COMMON200\",\"message\":\"리뷰 수정 성공\"}"
                    )
            )
    )
    ResponseEntity<ReviewResponseDto> updateReview(
            @Parameter(description = "리뷰 ID") @PathVariable UUID reviewId,
            @Parameter(description = "수정할 리뷰 데이터") @RequestBody UpdatedReviewRequestDto request,
            @Parameter(description = "유저 ID (X-User-Id 헤더)") @RequestHeader("X-User-Id") UUID userId
    );

    @Operation(
            summary = "리뷰 삭제 API",
            description = "사용자가 작성한 리뷰를 삭제합니다. 작성자 본인만 삭제할 수 있습니다.",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponse(
            responseCode = "204",
            description = "리뷰 삭제 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "success",
                            value = "{\"code\":\"COMMON204\",\"message\":\"리뷰 삭제 성공\"}"
                    )
            )
    )
    ResponseEntity<Void> deleteReview(
            @Parameter(description = "리뷰 ID") @PathVariable UUID reviewId,
            @Parameter(description = "유저 ID (X-User-Id 헤더, 없으면 더미 값 사용)")
            @RequestHeader(value = "X-User-Id", required = false) UUID userId
    );

    @Operation(
            summary = "상품별 리뷰 목록 조회 API",
            description = "특정 상품의 리뷰 목록을 페이지네이션 형태로 조회합니다. 기본 정렬은 작성일(createdAt) 내림차순입니다.",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @ApiResponse(
            responseCode = "200",
            description = "리뷰 목록 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "success",
                            value = "{\"code\":\"COMMON200\",\"message\":\"리뷰 목록 조회 성공\"}"
                    )
            )
    )
    ResponseEntity<ProductReviewListResponseDto> getProductReviews(
            @Parameter(description = "상품 ID") @PathVariable UUID productId,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기 (기본값: 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "정렬 기준 (기본값: createdAt)") @RequestParam(defaultValue = "createdAt") String sortBy
    );

}
