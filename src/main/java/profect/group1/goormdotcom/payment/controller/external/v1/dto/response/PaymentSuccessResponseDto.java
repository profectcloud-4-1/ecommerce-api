package profect.group1.goormdotcom.payment.controller.external.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import profect.group1.goormdotcom.payment.domain.enums.PayType;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record PaymentSuccessResponseDto (
        String mId,
        String paymentKey,

        @JsonProperty("orderNumber")
        @JsonAlias("orderId")
        String orderId,
        String orderName,
        String status,
        OffsetDateTime requestedAt,
        OffsetDateTime approvedAt,
        String currency,
        String totalAmount,
        String balanceAmount,
        String suppliedAmount,
        String method
) {

}