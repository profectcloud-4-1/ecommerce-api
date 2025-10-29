package profect.group1.goormdotcom.review.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "review-to-order")
public interface OrderClient {
    /**
     * userId + productId로 orderId 조회
     */
    @GetMapping("/api/v1/orders/search")
    UUID getOrderIdByUserAndProduct(@RequestParam("customerId") UUID customerId,
                                    @RequestParam("productId") UUID productId);

}
