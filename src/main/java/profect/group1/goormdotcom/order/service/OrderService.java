package profect.group1.goormdotcom.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import profect.group1.goormdotcom.order.controller.dto.OrderRequestDto;
import profect.group1.goormdotcom.order.controller.dto.OrderResponseDto;
import profect.group1.goormdotcom.order.controller.mapper.OrderDtoMapper;
import profect.group1.goormdotcom.order.repository.*;
import profect.group1.goormdotcom.order.repository.entity.*;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    // 공통코드 - 주문 상태
    private static final String ORD0001 = "ORD0001"; // 대기
    private static final String ORD0002 = "ORD0002"; // 완료
    private static final String ORD0003 = "ORD0003"; // 취소

    // private static final String PaymentService;
    // private static final String Stock;
    // private static final String DelieveryService;
 
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    // private final StockRepository stockRepository;
    private final CommonCodeRepository commonCodeRepository;

    private CommonCodeEntity code(String group, String c) {
        return commonCodeRepository.findByCodeGroupAndCode(group, c)
                .orElseThrow(() -> new IllegalArgumentException("invalid code: " + group + "/" + c));
    }

    // 1) 주문 생성: 재고 선차감(예약) + 주문(PENDING) -> 주문 완료 하면 
    // 재고 확인 먼저
    public OrderResponseDto create(OrderRequestDto req) {
        // 주문 저장
        OrderEntity order = OrderEntity.builder()
                .id(UUID.randomUUID())
                .customerId(req.getCustomerId())
                .sellerId(req.getSellerId())
                .productId(req.getProductId())
                .quantity(req.getQuantity())
                .orderName(req.getOrderName())
                .totalAmount(req.getTotalAmount())
                // .orderDate(LocalDateTime.now())
                // .currentCode(ORD0001) // 초기 상태: 대기
                .createdAt(LocalDateTime.now())
                .build();
        OrderEntity saved = orderRepository.save(order);
        appendOrderStatus(saved.getId(), ORD0001);
        touchUpdatedAt(saved);
        // 주문 상태: PENDING   
        // 이후 결제 생성은 별도 처리 (PaymentService에서 처리할 예정)
        // paymentClient.requestPayment(saved.getId(), saved.getTotalAmount());
        // deliveryClient.requestShipment(saved.getId());
        OrderStatusEntity current = latestStatus(saved.getId());
        return OrderResponseDto.fromEntity(saved, current);
    }
    public OrderResponseDto cancel(UUID orderId){
        OrderEntity order = findOrderOrThrow(orderId);
        
        // DeliveryClient.DeliveryState state = deliveryClient.getState(orderId);
        // switch(state){
        //     case BEFORE_DELIVERY -> {
        //         //주문 취소 시퀀스 - 상품 발송 전
        //         paymentClient.cancelPayment(orderId);   // 3. 결제 취소 요청
        //         appendOrderStatus(orderId, ORD0003);    // 4. 상태 이력: 취소
        //         touchUpdatedAt(order);
        //         return OrderResponseDto.fromEntity(order);
        //         return OrderResposeDto.fromEntity(order, latestStatus(orderId); -> GPT는 이거 권장
        //     }
        //     case IN_TRANSIT, DELIVERED -> {
        //         // 주문 취소 시퀀스 - 배송 완료 이후 (또는 배송 중) → 반송 → 결제 취소
        //         deliveryClient.requestReturn(orderId);  // 3. 반송 요청 (비동기)
        //         // ❗주: 반송 완료 이벤트 수신 후에 payment.cancelPayment(orderId) 호출 → 최종 CANCELED 기록
        //         // 여기서는 즉시 취소 확정하지 않고, 반송 완료 이벤트 핸들러에서 최종 취소 처리 권장
        //         return OrderResposeDto.fromEntity(order, latestStatus(orderId);
        //     }
        //     case RETURNED -> {
        //         // 반송이 이미 완료되었다면 이제 결제 취소 후 취소 확정
        //         paymentClient.cancelPayment(orderId);
        //         appendOrderStatus(orderId, ORD0003);
        //         touchUpdatedAt(order);
        //         OrderStatusEntity current = orderStatusRepository // -> 이 부분 앞에 있음
        //         .findTop1ByOrder_IdOrderByCreatedAtDesc(orderId)
        //         .orElseThrow(() -> new IllegalStateException("상태 이력이 없습니다."));
        //         return OrderResposeDto.fromEntity(order, latestStatus(orderId);
        //     }
        
        // default -> throw new IllegalStateException(
        //     "배송 상태를 확인할 수 없습니다. orderId=" + orderId
        // ); // ✅ default에서도 반드시 return or throw
            return OrderResponseDto.fromEntity(order, latestStatus(orderId));
    }
    //배송 완료 시퀀스 딜리버리 콜백
    public OrderResponseDto completeDelivery(UUID orderId) {
        OrderEntity order = findOrderOrThrow(orderId);
    //     DelieveyClient.DelieveryState state = deliveryClient.getState(orderId);
    //     if (state == DelieveryClient.DeliveryState.BEFORE_SHIPMENT){
    //         throw new IllegalStateException("배송 중 단게에서는 주문 완료 불가")
    //     }
    //     appendOrderStatus(orderId, ORD0002);
    //     touchUpdatedAt(order);
        return OrderResponseDto.fromEntity(order, latestStatus(orderId));
    // return OrderResponseDto.fromEntity(order, latestStatus(orderId));
    }
        //단건 조회
        // @Transactional(readOnly = true)
        // public OrderResponseDto getOne(UUID id) {
        //     OrderEntity e = orderRepository.findById(id).orElseThrow();
        //     var current = orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(id)
        //             .orElseThrow(() -> new IllegalStateException("상태 이력이 없습니다."));
        //     return OrderDtoMapper.toResponseDto(e, current.getStatus().getCode(), current.getStatus().getCode());

    //단건 조회 최신
    @Transactional(readOnly = true)
    public OrderResponseDto getOne(UUID id) {
        OrderEntity e = findOrderOrThrow(id);
    // // var current = orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(id)
    // //     .orElseThrow(() -> new IllegalStateException("상태 이력이 없습니다."));
    // // return OrderDtoMapper.toResponseDto(e, current.getStatus().getCode(), current.getStatus().getCode());
        return OrderResponseDto.fromEntity(e, latestStatus(id));
    }
    //전체 조회
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAll() {
        List<OrderEntity> entities = orderRepository.findAll();
        return entities.stream()
            .map(e -> OrderResponseDto.fromEntity(e, latestStatus(e.getId())))
            .toList();
    }
            // {
            //     var current = orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(e.getId())
            //             .orElseThrow(() -> new IllegalStateException("상태 이력이 없습니다."));
            //     return OrderDtoMapper.toResponseDto(e, current.getStatus().getCode(), current.getStatus().getCode());
            // }
            // ).collect(Collectors.toList());
        // }
    //상태 이력 추가
    private void appendOrderStatus(UUID orderId, String nextCode){
        OrderEntity order = findOrderOrThrow(orderId);
        LocalDateTime now = LocalDateTime.now();

    //     orderStatusRepository.save(
    //         OrderStatusEntity.builder()
    //             .id(UUID.randomUUID())
    //             .order(order)
    //             .status(code(ORDER_STATUS, nextCode))
    //             .createdAt(now)
    //             .build()
    //     );
    }
    //최신 상태 조회
    @Transactional(readOnly = true)
    private OrderStatusEntity latestStatus(UUID orderId){
        return orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(orderId)
            .orElseThrow(()-> new IllegalStateException("상태 이력이 없음. orderId=" + orderId));

    }
    //updatedAt 갱신
    private void touchUpdatedAt(OrderEntity order){
        orderRepository.save(order.toBuilder().updatedAt(LocalDateTime.now()).build());
    }
    // 내부 공통
    // private void appendOrderStatus(UUID orderId, String nextCode) {
    //     // OrderEntity order = orderRepository.findById(orderId).orElseThrow();
    //     OrderEntity order = findOrderOrThrow(orderID);
    //     var now = LocalDateTime.now();

    //     orderStatusRepository.save(
    //         OrderStatusEntity.builder()
    //             .id(UUID.randomUUID())
    //             .order(order)
    //             .status(code(ORDER_STATUS, nextCode))
    //             .createdAt(now)
    //             .build());
    //     orderRepository.save(order.toBuilder().updatedAt(now).build());
    // }
    //주문 조회
    private OrderEntity findOrderOrThrow(UUID id){
        return orderRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + id));
    }






    // private void appendOrderStatus(UUID orderId, String nextCode) {
    // OrderEntity order = orderRepository.findById(orderId).orElseThrow();
    // var now = LocalDateTime.now();
    // orderStatusRepository.save(OrderStatusEntity.builder()
    //         .id(UUID.randomUUID())
    //         .order(order)
    //         .status(code(ORDER_STATUS, nextCode))
    //         .createdAt(now)
    //         .build());
    // orderRepository.save(order.toBuilder().updatedAt(now).build());
    // }


    //재고 조회 인데 이건 내가 안할듯

    // private void restoreStock(OrderEntity order) {
    //     StockEntity stock = stockRepository.findByProductIdForUpdate(order.getProductId())
    //             .orElseThrow(() -> new IllegalStateException("재고가 없습니다. productId=" + order.getProductId()));
    //     stock.increase(order.getQuantity());
    //     stockRepository.save(stock);
    // }
}