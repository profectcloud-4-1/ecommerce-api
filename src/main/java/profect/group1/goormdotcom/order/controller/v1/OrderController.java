package profect.group1.goormdotcom.order.controller.v1;

import java.util.UUID;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import profect.group1.goormdotcom.order.controller.dto.OrderRequestDto;
import profect.group1.goormdotcom.order.controller.dto.OrderResponseDto;
import profect.group1.goormdotcom.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    //주문생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@Valid @RequestBody OrderRequestDto req) {
        return ResponseEntity.ok(orderService.create(req));
    }
    // @GetMapping({"/{id}"})
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOne(@PathVariable UUID id){
        return ResponseEntity.ok(orderService.getOne(id));
    }
    //발송 전 취소()
    // @GetMapping("/{id}/cancel") 
    // public ResponseEntity<void> cancelOrder(@PathVariable UUID id){
    //     orderServise.cancelBeforeShip
    // }

}
