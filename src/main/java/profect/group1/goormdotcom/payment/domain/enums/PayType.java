package profect.group1.goormdotcom.payment.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import profect.group1.goormdotcom.apiPayload.exceptions.handler.PaymentHandler;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PayType {
    CARD("카드"),
    TOSSPAY("토스페이");

    private final String description;

}
