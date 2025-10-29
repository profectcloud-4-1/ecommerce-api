package profect.group1.goormdotcom.order.domain.mapper;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.order.domain.Order;
import profect.group1.goormdotcom.order.repository.entity.OrderEntity;
import profect.group1.goormdotcom.order.domain.enums.OrderStatus;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    public Order toDomain(OrderEntity entity) {
        OrderStatus status = OrderStatus.fromCode(entity.getStatus());
        return Order.builder()
            .id(entity.getId())
            .customerId(entity.getCustomerId())
            .status(status)
            .createdAt(entity.getCreatedAt())
            .orderName(entity.getOrderName())
            .build();
    }

    public OrderEntity toEntity(Order order) {
        return OrderEntity.builder()
            .id(order.getId())
            .customerId(order.getCustomerId())
            .status(order.getStatus().getCode())
            .orderName(order.getOrderName())
            .build();
    }
}