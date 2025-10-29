package profect.group1.goormdotcom.delivery.controller.external.v1.dto.response;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import profect.group1.goormdotcom.delivery.domain.Delivery;

@Getter
@Builder
@AllArgsConstructor
public class DeliveryResponseDto {
    private Delivery delivery;
}