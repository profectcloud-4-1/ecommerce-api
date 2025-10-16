package profect.group1.goormdotcom.brand.repository.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Comment("입점 브랜드")
@Table(name = "p_brand")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at IS NULL")
public class BrandEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "name", nullable = false)
    @Comment("이름")
    private String name;
    
    @Column(name = "description")
    @Comment("설명")
    private String description;

    @Column(name = "website")
    @Comment("대표 웹사이트 URL")
    private String website;

}
