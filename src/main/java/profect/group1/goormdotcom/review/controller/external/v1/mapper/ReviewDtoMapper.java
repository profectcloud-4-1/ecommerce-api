package profect.group1.goormdotcom.review.controller.external.v1.mapper;

import profect.group1.goormdotcom.review.controller.external.v1.dto.CreateReviewRequestDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.ReviewResponseDto;
import profect.group1.goormdotcom.review.domain.Review;

import java.util.UUID;

public class ReviewDtoMapper {
    //Dto -> Domain 변환
    /**
     * RequestDto + 컨텍스트 정보 → Domain (생성)
     *
     * @param dto 클라이언트 요청 (productId, rating, content, imageUrl)
     * @param userId 인증된 사용자 ID (Security Context에서)
     * @param orderId 배송 ID (Service에서 조회)
     */
    public static Review toDomain(CreateReviewRequestDto dto, UUID userId, UUID orderId) {
        return Review.create(
                userId,              // Context에서
                dto.getProductId(),  // DTO에서
                orderId,          // Service에서 조회
                dto.getRating(),     // DTO에서
                dto.getContent()   // DTO에서
        );
    }

    /**
     * Domain → ResponseDto
     * 응답: review_id, user_id, rating, content, image_url, created_at
     */

    public static ReviewResponseDto toResponseDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),          // reviewId
                review.getRating(),      // rating
                review.getUserId(),      // userId
                review.getContent(),     // content
                review.getCreatedAt()    // createdAt
        );
    }

}
