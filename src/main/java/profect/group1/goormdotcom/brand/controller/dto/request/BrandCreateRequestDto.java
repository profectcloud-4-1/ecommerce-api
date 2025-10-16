package profect.group1.goormdotcom.brand.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

// [POST /brands] request payload
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandCreateRequestDto {
    @Schema(description = "이름")
    @NotBlank
    @NotEmpty
    private String name;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "대표 웹사이트 URL")
    @URL(message = "웹사이트 URL 형식이 올바르지 않습니다.")
    private String website;
}