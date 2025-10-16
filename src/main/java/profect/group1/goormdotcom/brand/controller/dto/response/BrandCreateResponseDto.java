package profect.group1.goormdotcom.brand.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

// [POST /brands] response
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandCreateResponseDto {
    @Schema(description = "브랜드 ID")
    @NotBlank
    private String id;

    public static BrandCreateResponseDto of(String id) {
        return new BrandCreateResponseDto(id);
    }
}