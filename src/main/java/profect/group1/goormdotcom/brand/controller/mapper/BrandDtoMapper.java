package profect.group1.goormdotcom.brand.controller.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandCreateRequestDto;
import profect.group1.goormdotcom.brand.domain.Brand;

@Component
public class BrandDtoMapper {
    public Brand toDomainFromCreateRequestDto(BrandCreateRequestDto dto) {
        return Brand.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .website(dto.getWebsite())
            .build();
    }
}
