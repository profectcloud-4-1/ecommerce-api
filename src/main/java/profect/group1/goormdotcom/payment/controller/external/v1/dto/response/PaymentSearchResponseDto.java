package profect.group1.goormdotcom.payment.controller.external.v1.dto.response;

import profect.group1.goormdotcom.payment.domain.enums.PayType;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PaymentSearchResponseDto (
    List<Item> items,
    Pagination pagination
)  {
    public record Item(
            UUID paymentId,
            String orderNumber,
            String orderName,
            Long amount,
            String status,
            String payType,
            LocalDateTime statusAt,
            LocalDateTime approvedAt,
            LocalDateTime cancelledAt
    ) {}

    public record Pagination(
            int page,
            int size,
            boolean hasNext,
            boolean isLast
    ) {}
}
