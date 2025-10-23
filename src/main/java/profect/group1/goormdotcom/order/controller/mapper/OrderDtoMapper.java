package profect.group1.goormdotcom.order.controller.mapper;


import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.order.controller.dto.OrderRequestDto;
import profect.group1.goormdotcom.order.controller.dto.OrderResponseDto;
import profect.group1.goormdotcom.order.domain.Order;
import profect.group1.goormdotcom.order.repository.entity.OrderEntity;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OrderDtoMapper {

    /*Entitly + 현재 상태(code/name) -> response Dto */
    public static OrderResponseDto toResponseDto(OrderEntity entity, String statusCode, String statusName){
        return OrderResponseDto.builder()
        .id(entity.getId())
        .customerId(entity.getCustomerId())
        .sellerId(entity.getSellerId())
        // .orderDate(entity.getCreatedAt())
        .totalAmount(entity.getTotalAmount())
        // .currentStatuscode(StatusCode) // 주문 상태 코드 (추후에 변수 수정 예정) -> 상태 테이블 만들어서 관리
        // .currentStatusName(StatusName) // 주문 상태 명(추후에 변수 수정 예정) -> 상태 테이블 만들어서 관리
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .deletedAt(entity.getDeletedAt())
        .build();

    }
    public static OrderEntity toEntity(OrderRequestDto req){
        var now = LocalDateTime.now();
        return OrderEntity.builder()
                .id(UUID.randomUUID())
                .customerId(req.getCustomerId())
                .sellerId(req.getSellerId())
                .productId(req.getProductId())
                .quantity(req.getQuantity())
                .createdAt(now)
                .updatedAt(now)
                .totalAmount(req.getTotalAmount())
                .orderName(req.getOrderName())
                .build();

    }
}
