package profect.group1.goormdotcom.product.controller.external.v1.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import profect.group1.goormdotcom.product.controller.external.v1.dto.ProductResponseDto;
import profect.group1.goormdotcom.product.domain.Product;
import profect.group1.goormdotcom.product.domain.ProductImage;

@Component
public class ProductDtoMapper {
    
    public static ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(
            product.getName(), 
            product.getBrandId(), 
            product.getCategoryId(), 
            product.getDescription(),
            product.getPrice(), 
            product.getImages().stream().map(ProductImage::getImageUrl).toList()
        );
    }
}
