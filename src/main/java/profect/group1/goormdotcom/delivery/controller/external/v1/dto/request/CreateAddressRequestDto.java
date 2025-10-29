package profect.group1.goormdotcom.delivery.controller.external.v1.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateAddressRequestDto {
    @Schema(description = "브랜드 ID (BrandAddress 생성 시 필요)")
    private UUID brandId;

    @Schema(description = "주소")
    @NotBlank
    private String address;
    @Schema(description = "상세주소")
    @NotBlank
    private String addressDetail;
    @Schema(description = "우편번호")
    @NotBlank
    private String zipcode;
    @Schema(description = "전화번호")
    @NotBlank
    private String phone;
    @Schema(description = "수취인 이름")
    @NotBlank
    private String name;
}