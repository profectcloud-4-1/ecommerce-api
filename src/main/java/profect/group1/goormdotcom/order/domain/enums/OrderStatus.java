package profect.group1.goormdotcom.order.domain.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("ORD0001", "대기"),
    // PAID,
    // CREATED,
    COMPLETED("ORD0002", "완료"),
    CANCELLED("ORD0003", "취소");

    private final String code;
    private final String label;

    // DELETED

    public static OrderStatus fromCode(String code) {
        return Arrays.stream(OrderStatus.values())
            .filter(status -> status.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid order status code: " + code));
    }

}
