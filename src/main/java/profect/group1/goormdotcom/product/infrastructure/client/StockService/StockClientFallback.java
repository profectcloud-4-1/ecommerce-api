package profect.group1.goormdotcom.product.infrastructure.client.StockService;

import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import profect.group1.goormdotcom.product.infrastructure.client.StockService.dto.StockRequestDto;
import profect.group1.goormdotcom.product.infrastructure.client.StockService.dto.StockResponseDto;

import java.util.UUID;

@Slf4j
@Component
public class StockClientFallback implements StockClient{
    
    @Override
    public ApiResponse<StockResponseDto> registerStock(StockRequestDto stockRequestDto) {
        return ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), ErrorStatus._INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    @Override
    public ApiResponse<StockResponseDto> updateStock(UUID productId, Integer stockQuantity) {
        return ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), ErrorStatus._INTERNAL_SERVER_ERROR.getMessage(), null);
    }
}
