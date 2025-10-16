package profect.group1.goormdotcom.brand.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import profect.group1.goormdotcom.brand.domain.Brand;

// [GET /brands/{brandId}] response
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandReadResponseDto {
    @Schema(description = "브랜드 정보")
    @NotNull
    private Brand brand;

    public static BrandReadResponseDto of(Brand brand) {
        return new BrandReadResponseDto(brand);
    }
}