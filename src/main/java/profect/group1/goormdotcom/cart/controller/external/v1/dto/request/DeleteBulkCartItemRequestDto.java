package profect.group1.goormdotcom.cart.controller.external.v1.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBulkCartItemRequestDto {

    @NotNull(message = "장바구니 아이템 ID 목록은 필수입니다")
    @NotEmpty(message = "삭제할 아이템이 최소 하나는 있어야 합니다")
    private List<UUID> cartItemIds;
}
