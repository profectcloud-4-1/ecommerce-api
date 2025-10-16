package profect.group1.goormdotcom.brand.repository.mapper;

import profect.group1.goormdotcom.brand.repository.entity.BrandEntity;
import profect.group1.goormdotcom.brand.domain.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public Brand toDomain(BrandEntity entity) {
        return Brand.builder()
            .id(entity.getId())
            .createdAt(entity.getCreatedAt())
            .name(entity.getName())
            .description(entity.getDescription())
            .website(entity.getWebsite())
            .build();
    }

    public BrandEntity toEntity(Brand domain) {
        return BrandEntity.builder()
            .id(domain.getId())
            .createdAt(domain.getCreatedAt())
            .name(domain.getName())
            .description(domain.getDescription())
            .website(domain.getWebsite())
            .build();
    }
}
