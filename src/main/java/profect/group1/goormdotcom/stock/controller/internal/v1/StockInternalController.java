package profect.group1.goormdotcom.stock.controller.internal.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.stock.controller.internal.v1.dto.ProductStockAdjustmentRequestDto;
import profect.group1.goormdotcom.stock.controller.internal.v1.dto.StockAdjustmentRequestDto;
import profect.group1.goormdotcom.stock.controller.internal.v1.dto.StockAdjustmentResponseDto;
import profect.group1.goormdotcom.stock.service.StockService;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.SuccessStatus;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/internal/v1/stock")
@RequiredArgsConstructor
public class StockInternalController implements StockInternalApiDocs {

    private final StockService stockService;

    
    @PostMapping("/decrease")
    public ApiResponse<StockAdjustmentResponseDto> decreaseStocks(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    ) {
        Map<UUID, Integer> requestedQuantityMap = new HashMap<UUID, Integer>();
        for (ProductStockAdjustmentRequestDto dto : stockAdjustmentRequestDto.products()) {
            requestedQuantityMap.put(dto.productId(), dto.requestedStockQuantity());
        }

        // TODO: 재시도 로직 필요
        Boolean status = stockService.decreaseStocks(requestedQuantityMap);
        return ApiResponse.of(SuccessStatus._OK, new StockAdjustmentResponseDto(status, new ArrayList<UUID>(requestedQuantityMap.keySet())));
    }

    @PostMapping("/increase")
    public ApiResponse<StockAdjustmentResponseDto> increaseStocks(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    ) {
        Map<UUID, Integer> requestedQuantityMap = new HashMap<UUID, Integer>();
        for (ProductStockAdjustmentRequestDto dto : stockAdjustmentRequestDto.products()) {
            requestedQuantityMap.put(dto.productId(), dto.requestedStockQuantity());
        }

        Boolean status = stockService.increaseStocks(requestedQuantityMap);
        return ApiResponse.of(SuccessStatus._OK, new StockAdjustmentResponseDto(status, new ArrayList<UUID>(requestedQuantityMap.keySet())));
    }
    
}
