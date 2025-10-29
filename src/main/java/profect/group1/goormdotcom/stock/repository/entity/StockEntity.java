package profect.group1.goormdotcom.stock.repository.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import profect.group1.goormdotcom.common.domain.BaseEntity;
import profect.group1.goormdotcom.stock.domain.exception.InsufficientStockException;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_stock")
// @Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "update p_stock set deleted_at = NOW() WHERE id = ?")
public class StockEntity extends BaseEntity{
    
    @Id
    private UUID id;

    @Version
    private long version;
    
    @Column(name = "product_id", unique=true)
    private UUID productId;
    @Column(name = "stock_quantity")
    private int stockQuantity;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public StockEntity(
        UUID id,
        UUID productId,
        int stockQuantity
    ) {
        this.id = id;
        this.productId = productId;
        this.stockQuantity = stockQuantity;
    }

    public void updateQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void decreaseQuantity(int requestedQuantity) {
        if (this.stockQuantity - requestedQuantity < 0) {
            throw new InsufficientStockException();
        }

        this.stockQuantity -= requestedQuantity;
    }

    public void increaseQuantity(int requestedQuantity) {
        this.stockQuantity += requestedQuantity;
    }


}
