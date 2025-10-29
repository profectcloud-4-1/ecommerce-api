package profect.group1.goormdotcom.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profect.group1.goormdotcom.review.repository.entity.ReviewImageEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, UUID> {

    // 특정 리뷰의 이미지 조회
    Optional<ReviewImageEntity> findByReviewId(UUID reviewId);

}
