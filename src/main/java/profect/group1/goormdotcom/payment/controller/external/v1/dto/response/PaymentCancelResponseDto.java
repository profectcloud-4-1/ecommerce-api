package profect.group1.goormdotcom.payment.controller.external.v1.dto.response;

import profect.group1.goormdotcom.payment.domain.enums.PayType;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PaymentCancelResponseDto (
        String paymentKey,
        String status,
        List<CancelEntry> cancels
) {
    public record CancelEntry(
            Long cancelAmount,            //취소 금액
            String cancelReason,             //취소 사유
            Long refundableAmount,        //남은 환불 가능 금액
            OffsetDateTime canceledAt,       //취소 시각
            String transactionKey,           //거래 키
            String cancelStatus              //취소 상태
    ) {}
}
