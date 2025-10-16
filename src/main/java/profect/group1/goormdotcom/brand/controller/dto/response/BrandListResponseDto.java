package profect.group1.goormdotcom.brand.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import profect.group1.goormdotcom.brand.domain.Brand;

// [GET /brands] response
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandListResponseDto {
    @Schema(description = "브랜드 목록")
    @NotNull
    private List<Brand> list;

    public static BrandListResponseDto of(List<Brand> list) {
        return new BrandListResponseDto(list);
    }
}