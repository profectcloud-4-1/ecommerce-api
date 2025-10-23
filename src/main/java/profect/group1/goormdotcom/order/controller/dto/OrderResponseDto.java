package profect.group1.goormdotcom.order.controller.dto;

import java.util.UUID;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import profect.group1.goormdotcom.order.domain.OrderStatus;
import profect.group1.goormdotcom.order.repository.entity.OrderEntity;
import profect.group1.goormdotcom.order.repository.entity.OrderStatusEntity;

@Getter
@Builder
@AllArgsConstructor

public class OrderResponseDto {
    private UUID id;
    private UUID customerId;
    private UUID sellerId;

    // private LocalDateTime orderDate;
    private int totalAmount;

    // private OrderStatus orderStatus;

    private String currentStatusCode; // 예: ord0001
    private String currentStatusName; // 예 : PENDING

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    /**
     * Entity → DTO 변환용 정적 메서드
     */
    public static OrderResponseDto fromEntity(OrderEntity entity, 
                                                OrderStatusEntity current){  
        String code = current.getStatus().getCode();
        String name = current.getStatus().getLabel();

        return OrderResponseDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .sellerId(entity.getSellerId())
                // .orderDate(entity.getOrderDate())
                .totalAmount(entity.getTotalAmount())
                // .orderStatus(entity.getOrderStatus())
                .currentStatusCode(code)
                .currentStatusName(name)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();

    }
}
