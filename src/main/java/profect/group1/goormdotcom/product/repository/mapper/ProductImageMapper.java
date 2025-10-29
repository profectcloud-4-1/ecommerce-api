package profect.group1.goormdotcom.product.repository.mapper;

import profect.group1.goormdotcom.product.domain.ProductImage;
import profect.group1.goormdotcom.product.repository.entity.ProductImageEntity;

public class ProductImageMapper {
    public static ProductImage toDomain(ProductImageEntity entity) {
        return new ProductImage(
            entity.getId(),
            entity.getProductId()
        );
    }

    public static ProductImage toDomainWithImage(ProductImageEntity entity, String imageUrl) {
        return new ProductImage(
            entity.getId(),
            entity.getProductId(),
            imageUrl,
            entity.getDeletedAt()
        );
    }
}
