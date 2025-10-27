package profect.group1.goormdotcom.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import profect.group1.goormdotcom.order.client.DeliveryClient; //?
import profect.group1.goormdotcom.order.client.PaymentClient; //?
import profect.group1.goormdotcom.order.client.StockClient;
import profect.group1.goormdotcom.order.controller.dto.OrderItemDto;
import profect.group1.goormdotcom.order.controller.dto.OrderRequestDto;
import profect.group1.goormdotcom.order.domain.Order;
import profect.group1.goormdotcom.order.domain.enums.OrderStatus; //?
import profect.group1.goormdotcom.order.domain.mapper.OrderMapper;
import profect.group1.goormdotcom.order.repository.OrderProductRepository;
import profect.group1.goormdotcom.order.repository.OrderRepository;
import profect.group1.goormdotcom.order.repository.OrderStatusRepository;
import profect.group1.goormdotcom.order.repository.entity.OrderEntity;
import profect.group1.goormdotcom.order.repository.entity.OrderProductEntity;
import profect.group1.goormdotcom.order.repository.entity.OrderStatusEntity;

@Slf4j
@Service
// @Transactional
@RequiredArgsConstructor
public class OrderService {

    // private static final String PaymentService;
    // private static final String Stock;
    // private static final String DelieveryService;
 
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderMapper orderMapper;
    // private final StockRepository stockRepository;

    //Feign Clients
    private final StockClient stockClient;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;

    @Value("${features.external-call.stock-check:true}")
    private Boolean stockCheckEnabled;

    @Value("${features.external-call.payment-check:true}")
    private Boolean paymentCheckEnabled;

    @Value("${features.external-call.delivery-check:true}")
    private Boolean deliveryCheckEnabled;

    // @PersistenceContext
    // private EntityManager em;

    // 1) 주문 생성: 재고 선차감(예약) + 주문(PENDING) -> 주문 완료 하면 
    // 재고 확인 먼저
    @Transactional
    public Order create(OrderRequestDto req) {
        log.info("주문 생성 시작: customerId={}, itemCount={}", req.getCustomerId(), req.getItems().size());

        //재고 확인 api
        // for (OrderItemDto item : req.getItems()) {
        //     Boolean stockAvailable = stockClient.checkStock(item.getProductId(), item.getQuantity());
        //     if (!stockAvailable) {
        //         log.warn("재고 부족: productId={}", item.getProductId());
        //         throw new IllegalStateException("재고가 부족합니다.");
        //     }
        // }
        // log.info("재고 확인 완료");

        // 아이템 저장

        OrderEntity orderEntity = OrderEntity.builder()
                        .customerId(req.getCustomerId())
                        .sellerId(req.getSellerId())
                        .totalAmount(req.getTotalAmount())
                        .orderName("주문명")
                        .status(OrderStatus.PENDING.getCode())
                        .build();

        orderRepository.save(orderEntity);

        List<OrderProductEntity> lines = new ArrayList<>();
        for (OrderItemDto itemDto : req.getItems()) {
            String productName = (req.getOrderName() != null && !req.getOrderName().isBlank())
                    ? req.getOrderName() : "상품";
                    
            OrderProductEntity line = OrderProductEntity.builder()
                .order(orderEntity)
                .productId(itemDto.getProductId())
                .productName(productName)
                .quantity(itemDto.getQuantity())
                .totalAmount(req.getTotalAmount() / req.getItems().size())
                .build();
            lines.add(line);
        }

        orderProductRepository.saveAll(lines);

        log.info("주문 생성 완료: orderId={}, status=결제대기", orderEntity.getId());
        return orderMapper.toDomain(orderEntity);

        //최신 상태 조회 후 DTO 반환
        // OrderStatusEntity current = orderStatusRepository
        //     .findTop1ByOrder_IdOrderByCreatedAtDesc(order.getId())
        //     .orElse(null);

        // return OrderResponseDto.fromEntity(order, current);
    }

    public Order completePayment(UUID orderId, UUID paymentId) {
        log.info("결제 완료 처리 시작: orderId={}, paymentId={}", orderId, paymentId);

        OrderEntity order = findOrderOrThrow(orderId);
        // 실제 결제 완료 처리 요청청
        Boolean paymentVerified = paymentClient.verifyPayment(
            new PaymentClient.PaymentVerifyRequest(orderId, paymentId, order.getOrderName(), order.getTotalAmount())
        );
        if (!paymentVerified) {
            appendOrderStatus(orderId, OrderStatus.CANCELLED);
            throw new IllegalStateException("결제 실패");
        }
        log.info("결제 완료: orderId={}, paymentID={}", orderId, paymentId);

        //결제 완료 테스트트
        // Boolean paymentVerified = true;
        // if (paymentCheckEnabled) {
        //     paymentVerified = paymentClient.verifyPayment(
        //         new PaymentClient.PaymentVerifyRequest(orderId, paymentId, order.getOrderName(), order.getTotalAmount())
        //     );
        //     if (!paymentVerified) {
        //         appendOrderStatus(orderId, OrderStatus.CANCELLED);
        //         throw new IllegalStateException("결제 실패");
        //     }
        // } else {
        //     log.info("[DEV] 결제 확인 생략됨");
        // }
        
        // Boolean stockDecreased = stockClient.decreaseStock(product.getProductId(), product.getQuantity());
        // if (!stockDecreased) {
        //     log.error("재고 차감 실패: orderId={}, productId={}", orderId, product.getProductId());
        //     appendOrderStatus(orderId, OrderStatus.CANCELLED);
        //     throw new IllegalStateException("재고 차감에 실패했습니다.");
        // }
        // log.info("재고 차감 완료: orderId={}", orderId);

        // log.info("재고 차감 완료: orderId={}", orderId);

        //배송 요청
        // Boolean deliveryRequested = deliveryClient.requestDelivery(
        //     new DeliveryClient.DeliveryRequest(orderId, order.getCustomerId())
        // );

        // if (!deliveryRequested) {
        //     log.error("배송 요청 실패: orderId={}", orderId);
        //     appendOrderStatus(orderId, OrderStatus.CANCELLED);
        //     throw new IllegalStateException("배송 요청에 실패했습니다.");
        // }
        // log.info("배송 요청 완료: orderId={}", orderId);

//--------------------------------

        // //배송 요청 테스트
        // Boolean deliveryRequested = true;
        // if (deliveryCheckEnabled) {
        //     deliveryRequested = deliveryClient.requestDelivery(
        //         new DeliveryClient.DeliveryRequest(orderId, order.getCustomerId())
        //     );
        //     if (!deliveryRequested) {
        //         log.error("배송 요청 실패: orderId={}", orderId);
        //         appendOrderStatus(orderId, OrderStatus.CANCELLED);
        //         throw new IllegalStateException("배송 요청에 실패했습니다.");
        //     }
        //     log.info("배송 요청 완료: orderId={}", orderId);
        // } else {
        //     log.info("[DEV] 배송 요청 생략됨");
        // }
        // 배송 요청
        Boolean deliveryRequested = deliveryClient.requestDelivery(
            new DeliveryClient.DeliveryRequest(orderId, order.getCustomerId())
        );

        if (!deliveryRequested) {
            log.error("배송 요청 실패: orderId={}", orderId);
            appendOrderStatus(orderId, OrderStatus.CANCELLED);
            throw new IllegalStateException("배송 요청에 실패했습니다.");
        }
        log.info("배송 요청 완료: orderId={}", orderId);

        // 주문 상태 업데이트       
        appendOrderStatus(orderId, OrderStatus.COMPLETED);
        return orderMapper.toDomain(order);
    }
    // 결제 취소 (배송전)
    public Order delieveryBefore(UUID orderId, UUID paymentId) {
        log.info("반품 처리 시작: orderId={}, paymentId={}", orderId, paymentId);
        
        OrderEntity order = findOrderOrThrow(orderId);
        DeliveryClient.DeliveryStatusResponse deliveryStatus = deliveryClient.getDeliveryStatus(orderId);
        if (deliveryStatus.status() != DeliveryClient.DeliveryStatus.PENDING) {
            log.error("배송 준비 중이 아님: orderId={}", orderId);
            throw new IllegalStateException("배송 준비 중이 아닙니다.");
        }
        
        //결제 취소 요청
        Boolean cancelPayment = paymentClient.cancelPayment(
            new PaymentClient.PaymentCancelRequest(orderId, paymentId, order.getOrderName(), "반품")
        );
        if (!cancelPayment) {
            log.error("결제 취소 실패: orderId={}, paymentId={}", orderId, paymentId);
            throw new IllegalStateException("결제 취소에 실패했습니다.");
        }
        log.info("결제 취소 완료: orderId={}", orderId);
        
        // 재고 복구
        List<OrderProductEntity> products = orderProductRepository.findAll().stream()
            .filter(p -> p.getOrder().getId().equals(orderId))
            .toList();
        
        for (OrderProductEntity product : products) {
            Boolean stockIncreased = stockClient.increaseStock(product.getProductId(), product.getQuantity());
            if (!stockIncreased) {
                log.error("재고 복구 실패: orderId={}, productId={}", orderId, product.getProductId());
                throw new IllegalStateException("재고 복구에 실패했습니다.");
            }
        }
        log.info("재고 복구 완료: orderId={}", orderId);
        //배송 취소 요청
        Boolean cancelDelivery = deliveryClient.cancelDelivery(orderId);
        if(!cancelDelivery) {
            log.error("배송 취소 요청 실패: orderId={}", orderId);
            throw new IllegalStateException("배송 취소 요청에 실패했습니다.");
        }
        log.info("배송 취소 완료: orderId={}", orderId);
        // 상태 업데이트
        appendOrderStatus(orderId, OrderStatus.CANCELLED);
        log.info("주문 취소 처리 완료: orderId={}", orderId);
        return orderMapper.toDomain(order);
    }

    // 취소 로직(반송)
    public Order cancel(UUID orderId, UUID paymentId) {
        OrderEntity order = findOrderOrThrow(orderId);

        //배송 상태 확인
        DeliveryClient.DeliveryStatusResponse deliveryStatus = deliveryClient.getDeliveryStatus(orderId);
        if (deliveryStatus.status() != DeliveryClient.DeliveryStatus.FINISHED) {
            log.error("배송 완료 전: orderId={}, status={}", orderId, deliveryStatus.status());
            throw new IllegalStateException("배송 완료 후에만 취소 가능합니다. 현재 상태: " + deliveryStatus.status());
        }
          // 반송 요청
          Boolean returnRequested = deliveryClient.requestReturn(orderId);
          if (!returnRequested) {
              log.error("반송 요청 실패: orderId={}", orderId);
              throw new IllegalStateException("반송 요청에 실패했습니다.");
          }
          log.info("반송 요청 완료: orderId={}", orderId);
        //결제 취소 요청
        Boolean cancelPayment = paymentClient.cancelPayment(
            new PaymentClient.PaymentCancelRequest(orderId, paymentId, order.getOrderName(), "반품")
        );
        if (!cancelPayment) {
            log.error("결제 취소 실패: orderId={}, paymentId={}", orderId, paymentId);
            throw new IllegalStateException("결제 취소에 실패했습니다.");
        }
        log.info("결제 취소 완료: orderId={}", orderId);
        appendOrderStatus(orderId, OrderStatus.CANCELLED);
        return orderMapper.toDomain(order);
    }
    //단건 조회 최신
    @Transactional(readOnly = true)
    public Order getOne(UUID id) {
        OrderEntity e = findOrderOrThrow(id);
        OrderStatusEntity current = orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(id)
        .orElseThrow(() -> new IllegalStateException("상태 이력이 없습니다. orderId=" + id));
    // // return OrderDtoMapper.toResponseDto(e, current.getStatus().getCode(), current.getStatus().getCode());
        return  orderMapper.toDomain(e);
    }
    //전체 조회
    @Transactional(readOnly = true)
    public List<Order> getAll() {
        List<OrderEntity> entities = orderRepository.findAll();
        return entities.stream()
            .map(e -> {OrderStatusEntity current = orderStatusRepository
                .findTop1ByOrder_IdOrderByCreatedAtDesc(e.getId())
                .orElse(null);
                return  orderMapper.toDomain(e);})
            .toList();
    }
            // OrderResponseDto.fromEntity(e, latestStatus(e.getId())))
            // .toList();
    
            // {
            //     var current = orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(e.getId())
            //             .orElseThrow(() -> new IllegalStateException("상태 이력이 없습니다."));
            //     return OrderDtoMapper.toResponseDto(e, current.getStatus().getCode(), current.getStatus().getCode());
            // }
            // ).collect(Collectors.toList());
        // }
    //상태 이력 추가
    private void appendOrderStatus(UUID orderId, OrderStatus status){
        OrderEntity order = findOrderOrThrow(orderId);
        // .orElseThrow(() -> new IllegalArgumentException("주문없음:" + orderId));


        orderStatusRepository.save(
            OrderStatusEntity.builder()
                .id(UUID.randomUUID())
                .order(order)
                .status(status.getCode()) // = "ORD0002" 등 문자열
                .build()
        );
        orderRepository.save(order.toBuilder().build());
    }
    //최신 상태 조회
    @Transactional(readOnly = true)
    private OrderStatusEntity latestStatus(UUID orderId){
        return orderStatusRepository.findTop1ByOrder_IdOrderByCreatedAtDesc(orderId)
            .orElseThrow(()-> new IllegalStateException("상태 이력이 없음. orderId=" + orderId));

    }
    
    //주문 조회
    private OrderEntity findOrderOrThrow(UUID id){
        return orderRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. id=" + id));
    }
}
