package profect.group1.goormdotcom.product.infrastructure.client.StockService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.common.config.FeignConfig;
import profect.group1.goormdotcom.product.infrastructure.client.StockService.dto.StockRequestDto;
import profect.group1.goormdotcom.product.infrastructure.client.StockService.dto.StockResponseDto;

import java.util.UUID;

@FeignClient(
    name = "product-to-stock",
    fallback = StockClientFallback.class,
    configuration = FeignConfig.class
)
public interface StockClient {
    
    @PostMapping("/api/v1/stock")
    public ApiResponse<StockResponseDto> registerStock(
        @RequestBody StockRequestDto stockRequestDto
    );

    @PutMapping("/api/v1/stock/{productId}")
    public ApiResponse<StockResponseDto> updateStock(
        @PathVariable("productId") UUID productId,
        @RequestBody Integer stockQuantity
    );
}
