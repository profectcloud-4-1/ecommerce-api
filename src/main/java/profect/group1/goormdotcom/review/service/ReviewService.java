package profect.group1.goormdotcom.review.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import profect.group1.goormdotcom.review.controller.external.v1.dto.CreateReviewRequestDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.ProductReviewListResponseDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.ReviewResponseDto;
import profect.group1.goormdotcom.review.controller.external.v1.dto.UpdatedReviewRequestDto;
import profect.group1.goormdotcom.review.controller.external.v1.mapper.ReviewDtoMapper;
import profect.group1.goormdotcom.review.domain.Review;
import profect.group1.goormdotcom.review.repository.ReviewImageRepository;
import profect.group1.goormdotcom.review.repository.ReviewRepository;
import profect.group1.goormdotcom.review.repository.entity.ReviewEntity;
import profect.group1.goormdotcom.review.repository.entity.ReviewImageEntity;
import profect.group1.goormdotcom.review.repository.mapper.ReviewMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;



    // 용도 : presigned client 받아오기

    private final PresignedClient presignedClient;
    private final OrderClient orderClient;


    /**
     * 리뷰 생성 (POST)
     *
     * @param request 리뷰 요청 데이터
     * @param userId 인증된 사용자 ID
     * @return 생성된 리뷰 응답
     */
    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto request, UUID userId) {


        // 2. OrderService에서 orderId 조회
        UUID orderId;
        try {
            orderId = orderClient.getOrderIdByUserAndProduct(userId, request.getProductId());
        } catch (FeignException.NotFound e) {
            throw new IllegalStateException("해당 상품을 주문한 내역이 없어 리뷰를 작성할 수 없습니다.");
        }

        // 3. 중복 리뷰 검증 (같은 주문에 대해 이미 리뷰 작성했는지)
        if (reviewRepository.existsByOrderId(orderId)) {
            throw new IllegalStateException("이미 해당 주문에 대한 리뷰를 작성하셨습니다.");
        }

        // 기존 Feign 호출 부분 대신 임시 더미 값 반환
        //TODO: order에 api 넣으면 삭제
        //UUID orderId = UUID.randomUUID(); // 또는 null, 원하는 값


        // 4. Domain 객체 생성 (비즈니스 로직 검증 포함)
        Review review = ReviewDtoMapper.toDomain(request, userId, orderId);

        // 5. Review 저장 (p_review 테이블)
        ReviewEntity savedReviewEntity = reviewRepository.save(
                ReviewMapper.toEntity(review)
        );

        // 6. 이미지가 있으면 ReviewImage 저장 (p_review_image 테이블)
        ReviewImageEntity savedImageEntity = null;
        if (request.getFileId() != null) {  // 프론트에서 fileId 전달
            ReviewImageEntity imageEntity = ReviewImageEntity.builder()
                    .reviewId(savedReviewEntity.getId())  // FK
                    .fileId(request.getFileId())          // FK
                    .build();
            savedImageEntity = reviewImageRepository.save(imageEntity);
        }

        // 7. Domain으로 재구성 후 응답 반환
        Review savedReview = ReviewMapper.toDomain(savedReviewEntity);
        ReviewResponseDto response = ReviewDtoMapper.toResponseDto(savedReview);

        // 8. presignedClient 동기 호출
        if (savedImageEntity != null) {
            UUID fileId = savedImageEntity.getFileId();
            try {
                presignedClient.confirmUpload(fileId);
                log.info("✅ PresignedClient confirmUpload 호출 성공 (fileId: {})", fileId);
            } catch (Exception e) {
                log.error("❌ PresignedClient confirmUpload 호출 실패: {}", e.getMessage(), e);
                // fallback 처리: 예외를 던지거나, 별도 보상 로직 실행
            }
        }


        //return ReviewDtoMapper.toResponseDto(savedReview);
        return response;


    }



    /**
     * 상품별 리뷰 목록 조회 (GET)
     *
     * @param productId 상품 ID
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param sortBy 정렬 기준
     * @return 리뷰 목록 응답
     */

    public ProductReviewListResponseDto getProductReviews(UUID productId, int page, int size, String sortBy) {
        // 1. 페이징 및 정렬 설정
        Sort sort = sortBy.equals("rating")
                ? Sort.by(Sort.Direction.DESC, "rating")
                : Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        // 2. 리뷰 목록 조회
        Page<ReviewEntity> reviewPage = reviewRepository.findByProductId(productId, pageable);

        // 3. 각 리뷰에 presigned URL 매핑
        /*List<ReviewResponseDto> reviewResponses = reviewPage.getContent().stream()
            .map(reviewEntity -> {
            // 3-1. 리뷰에 연결된 이미지 정보 조회
            UUID fileId = reviewImageRepository.findByReviewId(reviewEntity.getId())
                    .map(ReviewImageEntity::getFileId)
                    .orElse(null);

            String presignedUrl = null;

            // 3-2. presigned 서비스 호출 (FeignClient)
            if (fileId != null) {
                try {
                    ResponseEntity<ObjectKeyResponseDto> response = presignedClient.getObjectKey(fileId);
                    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                        presignedUrl = response.getBody().getObjectKey(); // 또는 getPresignedUrl()
                    }
                } catch (Exception e) {
                    // presigned 서비스 호출 실패 시 로그만 출력
                    log.warn("Failed to fetch presigned URL for fileId: {}", fileId, e);
                }
            }

            // 3-3. 도메인 객체 생성 및 DTO 변환
            Review review = ReviewMapper.toDomain(reviewEntity);
            return ReviewDtoMapper.toResponseDto(review);
        })
        .toList();*/
        // 3. 각 리뷰 도메인 매핑 및 DTO 변환
        List<ReviewResponseDto> reviewResponses = reviewPage.getContent().stream()
                .map(reviewEntity -> {
                    // 리뷰 도메인 객체 생성 (이미지 URL 제외)
                    Review review = ReviewMapper.toDomain(reviewEntity);

                    // DTO 변환
                    return ReviewDtoMapper.toResponseDto(review);
                })
                .toList();

        // 4. 통계 정보 조회
        Double averageRating = reviewRepository.findAverageRatingByProductId(productId);
        long totalReviews = reviewRepository.countByProductId(productId);


        // 5. 응답 생성
        return new ProductReviewListResponseDto(
                productId,
                averageRating != null ? averageRating : 0.0,
                (int) totalReviews,
                reviewResponses,
                page,
                size,
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages()
        );
    }

    /**
     * 리뷰 단건 조회 (GET)
     * TODO: 추후 추가
     * @param reviewId 리뷰 ID
     * @return 리뷰 응답
     */
    /*
    public ReviewResponseDto getReview(UUID reviewId) {
        // 1. 리뷰 조회
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."
                ));

        // 2. 이미지 조회 (있으면)
        String imageUrl = reviewImageRepository.findByReviewId(reviewId)
                .map(ReviewImageEntity::getImageObject)
                .orElse(null);

        // 3. Domain 변환 후 응답
        Review review = ReviewMapper.toDomain(reviewEntity, imageUrl);
        return ReviewDtoMapper.toResponseDto(review);
    }*/

    /**
     * 리뷰 수정 (put service)
     * @param reviewId 리뷰 ID
     * @param request 수정 요청 데이터
     * @param userId 인증된 사용자 ID
     * @return 수정된 리뷰 응답
     */

    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, UpdatedReviewRequestDto request, UUID userId) {
        // 1. 리뷰 조회
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."));

        // 2. 권한 확인 (본인 리뷰인지)
        if (!reviewEntity.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 리뷰만 수정할 수 있습니다.");
        }

        // 3. 기존 이미지 조회
        /*String existingImageUrl = reviewImageRepository.findByReviewId(reviewId)
                .map(ReviewImageEntity::getImageObject)
                .orElse(null);*/

        // 4. Domain으로 변환 후 수정
        Review review = ReviewMapper.toDomain(reviewEntity);
        Review updatedReview = review.update(
                request.getRating(),
                request.getContent()
        );

        // 5. Entity 저장
        ReviewEntity updatedEntity = reviewRepository.save(
                ReviewMapper.toEntity(updatedReview)
        );


        // 7. 응답 반환
        /*String finalImageUrl = request.getImageUrl() != null
                ? request.getImageUrl()
                : existingImageUrl;
        Review savedReview = ReviewMapper.toDomain(updatedEntity);
        return ReviewDtoMapper.toResponseDto(savedReview);*/

        Review savedReview = ReviewMapper.toDomain(updatedEntity);
        return ReviewDtoMapper.toResponseDto(savedReview);
    }


    /**
     * 리뷰 삭제 (DELETE) - Soft Delete
     *
     * @param reviewId 리뷰 ID
     * @param userId 인증된 사용자 ID
     */
    @Transactional
    public void deleteReview(UUID reviewId, UUID userId) {
        // 1. 리뷰 조회
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "리뷰를 찾을 수 없습니다."));

        // 2. 권한 확인 (본인 리뷰인지)
        if (!reviewEntity.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"본인의 리뷰만 삭제할 수 있습니다.");
        }

        // 3. Soft Delete (JPA @SQLDelete 어노테이션이 자동 처리)
        reviewRepository.delete(reviewEntity);

        // 4. 이미지도 Soft Delete
        reviewImageRepository.findByReviewId(reviewId)
                .ifPresent(reviewImageRepository::delete);
    }

    /**
     * 이미지 업데이트 처리(fileId 기반)
     * - null: 변경 없음 (기존 유지)
     * - 빈 문자열: 이미지 삭제
     * - URL: 이미지 변경/추가
     */
    private void handleImageUpdate(UUID reviewId, UUID newFileId) {
        // 1. 변경 없음 → 아무것도 안 함
        if (newFileId == null) return;

        // 2️. 기존 이미지 존재 여부 확인
        Optional<ReviewImageEntity> existingImageOpt = reviewImageRepository.findByReviewId(reviewId);

        // 3️. fileId가 null → 삭제
        if (newFileId.toString().isBlank()) {
            existingImageOpt.ifPresent(reviewImageRepository::delete);
            return;
        }

        // 4️. fileId가 있으면 교체 or 새로 추가
        existingImageOpt.ifPresentOrElse(
                imageEntity -> {
                    // 기존 이미지 삭제 후 새로 저장
                    reviewImageRepository.delete(imageEntity);
                    ReviewImageEntity newImage = ReviewImageEntity.builder()
                            .reviewId(reviewId)
                            .fileId(newFileId)
                            .build();
                    reviewImageRepository.save(newImage);
                },
                () -> {
                    // 기존 이미지 없으면 새로 추가
                    ReviewImageEntity newImage = ReviewImageEntity.builder()
                            .reviewId(reviewId)
                            .fileId(newFileId)
                            .build();
                    reviewImageRepository.save(newImage);
                }
        );
    }



}
