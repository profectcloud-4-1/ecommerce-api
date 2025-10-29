package profect.group1.goormdotcom.category.repository.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Locale.Category;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.DialectOverride.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import profect.group1.goormdotcom.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Entity
@Table(name = "p_category")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLDelete(sql = "update p_category set deleted_at = NOW() where id = ?")
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity extends BaseEntity{

    @Id
    private UUID id;
    private UUID parentId;
    private String name;
    private LocalDateTime deletedAt;

    public CategoryEntity(
        UUID id,
        UUID parentId,
        String name      
    ) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
