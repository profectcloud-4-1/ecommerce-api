package profect.group1.goormdotcom.user.controller.external.v1.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserAddressRequestDto {
    @Schema(description = "주소")
    private String address;
    @Schema(description = "상세주소")
    private String addressDetail;
    @Schema(description = "우편번호")
    private String zipcode;
    @Schema(description = "전화번호")
    private String phone;
    @Schema(description = "수취인 이름")
    private String name;
    @Schema(description = "배송 메모")
    private String deliveryMemo;
}