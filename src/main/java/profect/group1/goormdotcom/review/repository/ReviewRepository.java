package profect.group1.goormdotcom.review.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import profect.group1.goormdotcom.review.repository.entity.ReviewEntity;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    // 특정 배송에 대한 리뷰 존재 여부 (중복 체크용)
    boolean existsByOrderId(UUID orderId);

    // 특정 상품의 리뷰 목록 조회 (페이징)
    Page<ReviewEntity> findByProductId(UUID productId, Pageable pageable);

    // 특정 상품의 평균 별점 조회
    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.productId = :productId")
    Double findAverageRatingByProductId(@Param("productId") UUID productId);

    // 특정 상품의 총 리뷰 개수
    long countByProductId(UUID productId);

    // 특정 상품의 리뷰 목록 조회 (나중에 구현)
    // List<ReviewEntity> findByProductId(UUID productId, Pageable pageable);
}
