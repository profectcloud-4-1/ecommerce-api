package profect.group1.goormdotcom.product.repository.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import profect.group1.goormdotcom.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@Entity
@Table(name = "p_product")
// @Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update p_product set deleted_at = NOW() where id = ?")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity extends BaseEntity{
    @Id
    private UUID id;

    @Column(name = "brand_id")
    private UUID brandId;
    @Column(name = "category_id")
    private UUID categoryId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "description")
    private String description;
    
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public ProductEntity(
        final UUID id,
        final UUID brandId,
        final UUID categoryId,
        final String name,
        final int price,
        final String description
    ) {
        this.id = id;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
