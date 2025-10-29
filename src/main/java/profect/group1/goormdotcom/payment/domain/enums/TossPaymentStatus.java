package profect.group1.goormdotcom.payment.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TossPaymentStatus {

    READY("PAY0000"),
    IN_PROGRESS("PAY0000"),
    WAITING_FOR_DEPOSIT("PAY0000"),
    DONE("PAY0001"),
    CANCELED("PAY0004"),
    PARTIAL_CANCELED("PAY0005"),
    ABORTED("PAY0002");

    private final String codePk;

    public static String getCodeByTossStatus(TossPaymentStatus status) {
        return TossPaymentStatus.valueOf(status.name()).getCodePk();
    }
}