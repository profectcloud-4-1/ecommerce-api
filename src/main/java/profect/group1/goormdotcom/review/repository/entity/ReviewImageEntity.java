package profect.group1.goormdotcom.review.repository.entity;


import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_review_image")
@SQLDelete(sql = "UPDATE p_review_image SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Builder
public class ReviewImageEntity {

    @Id
    private UUID id;

    // jpa 에러 방지용
    //TODO: 나중에 삭제
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if(this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @Column(name = "review_id", nullable = false)
    private UUID reviewId;

    private UUID fileId;

    //@Column(name = "image_object", nullable = false, length = 1024)
    //private String imageObject;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // JPA 기본 생성자
    protected ReviewImageEntity() {}

    // 전체 생성자
    public ReviewImageEntity(UUID id, UUID reviewId,UUID fileId,
                             LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.reviewId = reviewId;
        this.fileId = fileId;
        //this.imageObject = imageObject;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getReviewId() { return reviewId; }
    public UUID getFileId() { return fileId; }
    // String getImageObject() { return imageObject; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
}
