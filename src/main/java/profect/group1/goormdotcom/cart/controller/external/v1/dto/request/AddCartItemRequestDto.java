package profect.group1.goormdotcom.cart.controller.external.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequestDto {

    @NotNull(message = "상품 ID는 필수입니다")
    private UUID productId;

    @NotNull(message = "수량은 필수입니다")
    @Positive(message = "수량은 양수여야 합니다")
    private Integer quantity;

    @NotNull(message = "가격은 필수입니다")
    @Positive(message = "가격은 양수여야 합니다")
    private Integer price;
}
