package profect.group1.goormdotcom.order.controller.internal.v1;


import java.util.UUID;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import profect.group1.goormdotcom.order.service.OrderService;
import profect.group1.goormdotcom.order.domain.Order;

@RestController
@RequestMapping("/internal/v1/orders")
@RequiredArgsConstructor
public class OrderInternalController {

    private final OrderService orderService;

    //결제 완료
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Order> completePayment(@PathVariable UUID orderId){
        return ResponseEntity.ok(orderService.completePayment(orderId));
    }
 
}
