package profect.group1.goormdotcom.payment.controller.external.v1.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import profect.group1.goormdotcom.payment.domain.enums.Status;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentSearchRequestDto {
    private String status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toAt;
    private Long minAmount;
    private Long maxAmount;

    public static PaymentSearchRequestDto of(
            String status,
            LocalDateTime fromAt,
            LocalDateTime toAt,
            Long minAmount,
            Long maxAmount) {
        return PaymentSearchRequestDto.builder()
                .status(status)
                .fromAt(fromAt)
                .toAt(toAt)
                .minAmount(minAmount)
                .maxAmount(maxAmount)
                .build();
    }
}
