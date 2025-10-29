package profect.group1.goormdotcom.stock.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Stock {
    
    private UUID id;
    private UUID productId;
    private int stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Stock(
        UUID id,
        UUID productId,
        int stockQuantity
    ) {
        this.id = id;
        this.productId = productId;
        this.stockQuantity = stockQuantity;
    }
}
