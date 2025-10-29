package profect.group1.goormdotcom.review.repository.mapper;

import profect.group1.goormdotcom.review.domain.Review;
import profect.group1.goormdotcom.review.repository.entity.ReviewEntity;
import profect.group1.goormdotcom.review.repository.entity.ReviewImageEntity;

import java.util.UUID;

public class ReviewMapper {
    /**
     * Domain → Entity
     */
    public static ReviewEntity toEntity(Review review) {
        return new ReviewEntity(
                review.getId(),
                review.getUserId(),
                review.getProductId(),
                review.getOrderId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                null  // deletedAt은 null (신규 생성)
        );

        /**
         * .orderProductId(domain.getOrderProductId())
         *  .storeId(domain.getBrandId())
         *  .updatedAt(domain.getUpdatedAt())
         */
    }

    /**
     * Entity → Domain
     *
     */
    public static Review toDomain(ReviewEntity entity) {
        return Review.of(
                entity.getId(),
                entity.getUserId(),
                entity.getProductId(),
                entity.getOrderId(),
                entity.getRating(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }


}
