package profect.group1.goormdotcom.stock.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.stock.controller.dto.ProductStockAdjustmentRequestDto;
import profect.group1.goormdotcom.stock.controller.dto.StockAdjustmentRequestDto;
import profect.group1.goormdotcom.stock.controller.dto.StockAdjustmentResponseDto;
import profect.group1.goormdotcom.stock.controller.dto.StockRequestDto;
import profect.group1.goormdotcom.stock.controller.dto.StockResponseDto;
import profect.group1.goormdotcom.stock.controller.mapper.StockDtoMapper;
import profect.group1.goormdotcom.stock.domain.Stock;
import profect.group1.goormdotcom.stock.domain.exception.InsufficientStockException;
import profect.group1.goormdotcom.stock.service.StockService;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import profect.group1.goormdotcom.apiPayload.code.status.SuccessStatus;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController implements StockApiDocs {

    private final StockService stockService;
    private int MAX_RETRY = 5;

    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<StockResponseDto> registerStock(
        @RequestBody StockRequestDto stockRequestDto
    ) {
        Stock stock = stockService.registerStock(stockRequestDto.productId(), stockRequestDto.stockQuantity());
        return ApiResponse.of(SuccessStatus._OK, StockDtoMapper.toStockResponseDto(stock));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<StockResponseDto> updateStock(
        @PathVariable(value="productId") UUID productId, 
        @RequestBody int stockQuantity
    ) {
        Stock stock = stockService.updateStock(productId, stockQuantity);
        return ApiResponse.of(SuccessStatus._OK, StockDtoMapper.toStockResponseDto(stock));
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<StockResponseDto> getStock(
        @PathVariable(value = "productId") UUID productId
    ) {
        
        Stock stock = stockService.getStock(productId);
        return ApiResponse.of(SuccessStatus._OK, StockDtoMapper.toStockResponseDto(stock));        
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<UUID> deleteStock(
        @PathVariable(value = "productId") UUID productId
    ) {
        stockService.deleteStock(productId);
        return ApiResponse.of(SuccessStatus._OK, productId);
    }
    
    @PostMapping("/decrease")
    public ApiResponse<StockAdjustmentResponseDto> decreaseStocks(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    ) {
        Map<UUID, Integer> requestedQuantityMap = new HashMap<UUID, Integer>();
        for (ProductStockAdjustmentRequestDto dto : stockAdjustmentRequestDto.products()) {
            requestedQuantityMap.put(dto.productId(), dto.requestedStockQuantity());
        }

        Boolean status = stockService.decreaseStocks(requestedQuantityMap);
        return ApiResponse.of(SuccessStatus._OK, new StockAdjustmentResponseDto(status, new ArrayList<UUID>(requestedQuantityMap.keySet())));
    }

    @PostMapping("/decrease/optimistic")
    public ApiResponse<StockAdjustmentResponseDto> decreaseStocksWithOptimisitc(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    ) {
        Map<UUID, Integer> requestedQuantityMap = new HashMap<UUID, Integer>();
        for (ProductStockAdjustmentRequestDto dto : stockAdjustmentRequestDto.products()) {
            requestedQuantityMap.put(dto.productId(), dto.requestedStockQuantity());
        }
        int retryCount = 0;

        while (true) {
            try {                
                stockService.decreaseStocksWithOptimisticLock(requestedQuantityMap);
                break;
            } catch (InsufficientStockException e) {
                return ApiResponse.onFailure(ErrorStatus._INSUFFICIENT_STOCK_QUANTITY.getCode() , ErrorStatus._INSUFFICIENT_STOCK_QUANTITY.getMessage(), new StockAdjustmentResponseDto(false, new ArrayList<UUID>(requestedQuantityMap.keySet())));
            } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException e ) {
                // 재시도 로직
                retryCount += 1;
                if (retryCount > MAX_RETRY) { 
                    return ApiResponse.onFailure(ErrorStatus._ADJUST_STOCK_FAILED.getCode() , ErrorStatus._ADJUST_STOCK_FAILED.getMessage(), new StockAdjustmentResponseDto(false, new ArrayList<UUID>(requestedQuantityMap.keySet())));
                }

                // 백오프
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return ApiResponse.onFailure(ErrorStatus._CONFLICT.getCode(), "Interrupted",
                        new StockAdjustmentResponseDto(false, new ArrayList<>(requestedQuantityMap.keySet())));
                }
            } 
        }
        return ApiResponse.of(SuccessStatus._OK, new StockAdjustmentResponseDto(true, new ArrayList<UUID>(requestedQuantityMap.keySet())));
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
    
    @PostMapping("/increase/optimistic")
    public ApiResponse<StockAdjustmentResponseDto> increaseStocksWithOptimisitc(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    ) {
        Map<UUID, Integer> requestedQuantityMap = new HashMap<UUID, Integer>();
        for (ProductStockAdjustmentRequestDto dto : stockAdjustmentRequestDto.products()) {
            requestedQuantityMap.put(dto.productId(), dto.requestedStockQuantity());
        }
        int retryCount = 0;

        while (true) {
            try {                
                stockService.increaseStocksWithOptimisticLock(requestedQuantityMap);
                break;
            } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException e ) {
                // 재시도 로직
                retryCount += 1;
                if (retryCount > MAX_RETRY) { 
                    return ApiResponse.onFailure(ErrorStatus._ADJUST_STOCK_FAILED.getCode() , ErrorStatus._ADJUST_STOCK_FAILED.getMessage(), new StockAdjustmentResponseDto(false, new ArrayList<UUID>(requestedQuantityMap.keySet())));
                }

                // 백오프
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return ApiResponse.onFailure(ErrorStatus._CONFLICT.getCode(), "Interrupted",
                        new StockAdjustmentResponseDto(false, new ArrayList<>(requestedQuantityMap.keySet())));
                }
            } 
        }
        return ApiResponse.of(SuccessStatus._OK, new StockAdjustmentResponseDto(true, new ArrayList<UUID>(requestedQuantityMap.keySet())));
    }
}
