package profect.group1.goormdotcom.user.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;
import java.time.LocalDateTime;
import profect.group1.goormdotcom.user.domain.enums.UserRole;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;

@Entity
@Comment("사용자")
@Table(name = "p_user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted_at IS NULL")
public class UserJpaEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "email", nullable = false)
    @Comment("이메일")
    private String email;
    @Column(name = "password", nullable = false)
    @Comment("비밀번호")
    @JsonIgnore
    private String password;
    @Column(name = "name", nullable = false)
    @Comment("이름")
    private String name;
    
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("역할")
    private UserRole role;

    @Column(name = "brand_id")
    @Comment("소속 브랜드 ID (p_brand.id) (판매자가 아닐 경우 null)")
    private String brandId;
}