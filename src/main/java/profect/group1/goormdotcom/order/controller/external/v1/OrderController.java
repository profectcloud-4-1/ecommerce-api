package profect.group1.goormdotcom.order.controller.external.v1;


import java.util.UUID;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import profect.group1.goormdotcom.order.controller.external.v1.dto.OrderRequestDto;
import profect.group1.goormdotcom.order.service.OrderService;
import profect.group1.goormdotcom.order.domain.Order;
import profect.group1.goormdotcom.user.controller.auth.LoginUser;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    //주문생성 
    // *POST /api/v1/orders
    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderRequestDto req, @LoginUser UUID userId) {
        Order order = orderService.create(userId,req);
        return ResponseEntity.ok(order);
    }
    
    //배송 전 취소 (환불)
    @PostMapping("/{orderId}/cancel/delivery-before")
    public ResponseEntity<Order> deliveryBefore(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.delieveryBefore(orderId));
    }

    //배송 후 취소 (반송)
    @PostMapping("/{orderId}/cancel/return")
    public ResponseEntity<Order> cancel(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.cancel(orderId));
    }
 
    //전체 조회
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAll());
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable UUID id){
        return ResponseEntity.ok(orderService.getOne(id));
    }
}
