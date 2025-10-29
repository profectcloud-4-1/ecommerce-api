package profect.group1.goormdotcom.cart.controller.external.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequestDto {

	@NotNull(message = "수량은 필수입니다")
	@Positive(message = "수량은 양수여야 합니다")
	private Integer quantity;
}
