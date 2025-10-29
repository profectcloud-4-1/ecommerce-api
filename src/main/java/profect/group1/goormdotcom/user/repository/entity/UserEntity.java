package profect.group1.goormdotcom.user.repository.entity;

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
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import profect.group1.goormdotcom.common.domain.BaseEntity;

@Entity
@Comment("사용자")
@Table(name = "p_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "update p_user set deleted_at = NOW() where id = ?")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

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
    
    @Column(name = "role")
    @Comment("역할. common code - USER_ROLE")
    private String role;
}