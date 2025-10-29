package profect.group1.goormdotcom.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import profect.group1.goormdotcom.order.domain.enums.OrderStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder

public class Order {
    private UUID id;
    private UUID customerId;
    private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
    // private LocalDateTime orderDate;
    private int totalAmount;
    private OrderStatus status; // "ORD0001" 등 문자열
    // private OrderStatus orderStatus;
    private String orderName;

}