package profect.group1.goormdotcom.payment.controller.external.v1.dto.response;

import profect.group1.goormdotcom.payment.domain.enums.PayType;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.util.UUID;


public record PaymentResponseDto (
        UUID id,
        String payType, //제외?
        String status,
        Long amount
) {

}
