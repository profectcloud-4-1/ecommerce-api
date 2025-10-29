package profect.group1.goormdotcom.product.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {
    
    private UUID id;
    private UUID productId;
    private String imageUrl;
    private LocalDateTime deletedAt;   

    public void updateProductId(UUID productId) {
        this.productId = productId;
    }

    public ProductImage(
        UUID id,
        UUID productId
    ) {
        this.id = id;
        this.productId = productId;
    }

    public ProductImage(
        UUID id,
        UUID productId,
        String imageUrl
    ) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
    }
}
