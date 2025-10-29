package profect.group1.goormdotcom.review.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Review {
    private UUID id;
    private UUID userId;
    private UUID productId;
    private UUID orderId;
    private int rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Private 생성자
    private Review(UUID id, UUID userId, UUID productId, UUID orderId,
                   int rating, String content,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.orderId = orderId;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ===== 정적 팩토리 메서드 - 새 리뷰 생성 (POST용) =====
    public static Review create(UUID userId, UUID productId, UUID orderId,
                                int rating, String content) {
        // 비즈니스 규칙 검증
        validateRating(rating);
        validateContent(content);

        LocalDateTime now = LocalDateTime.now();
        return new Review(
                UUID.randomUUID(),  // 새 ID 생성
                userId,
                productId,
                orderId,
                rating,
                content,
                now,
                now
        );
    }

    // ===== 정적 팩토리 메서드 - 기존 리뷰 재구성 (DB 조회용) =====
    public static Review of(UUID id, UUID userId, UUID productId, UUID orderId,
                            int rating, String content,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Review(id, userId, productId, orderId,
                rating, content, createdAt, updatedAt);
    }

    // ===== 리뷰 수정 (UPDATE용) =====
    public Review update(int newRating, String newContent) {
        validateRating(newRating);
        validateContent(newContent);

        return new Review(
                this.id,
                this.userId,
                this.productId,
                this.orderId,
                newRating,
                newContent,
                this.createdAt,
                LocalDateTime.now()  // updatedAt 갱신
        );
    }

    // ===== 검증 로직 =====
    private static void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("별점은 1~5점 사이여야 합니다.");
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("리뷰 내용은 필수입니다.");
        }
        if (content.trim().length() < 10) {
            throw new IllegalArgumentException("리뷰 내용은 최소 10자 이상이어야 합니다.");
        }
        if (content.length() > 500) {
            throw new IllegalArgumentException("리뷰 내용은 500자를 초과할 수 없습니다.");
        }
    }

    // ===== Getters =====
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getProductId() { return productId; }
    public UUID getOrderId() { return orderId; }
    public int getRating() { return rating; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}