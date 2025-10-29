package profect.group1.goormdotcom.order.controller.external.v1.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
public class OrderRequestDto {

    private String orderName;

    @Min(0)
    private int totalAmount;

    @Schema(description = "배송지 주소")
    private String address;
    @Schema(description = "배송지 상세주소")
    private String addressDetail;
    @Schema(description = "배송지 우편번호")
    private String zipcode;
    @Schema(description = "배송지 전화번호")
    private String phone;
    @Schema(description = "수취인 이름")
    private String name;
    @Schema(description = "배송 메모")
    private String deliveryMemo;

    // @Min(1)
    // private int quantity; //테스트용 주문 수량량

    @Valid
    @NotEmpty
    private List<OrderItemDto> items;
}