package profect.group1.goormdotcom.order.domain;

import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder

public class Order {
    private UUID id;
    private UUID customerId;
    private UUID sellerId;
    private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
    private LocalDateTime orderDate;
    private int totalAmount;
    private OrderStatus orderStatus;
    private String orderName;

}
