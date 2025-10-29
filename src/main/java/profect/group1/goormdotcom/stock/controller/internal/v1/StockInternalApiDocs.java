package profect.group1.goormdotcom.stock.controller.internal.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;
import profect.group1.goormdotcom.stock.controller.internal.v1.dto.StockAdjustmentRequestDto;
import profect.group1.goormdotcom.stock.controller.internal.v1.dto.StockAdjustmentResponseDto;

@Tag(name = "재고 (internal)", description = "재고 관리 API (내부서비스간 통신용)")
public interface StockInternalApiDocs {

    

    @Operation(
        summary = "재고 차감",
        description = "여러 상품의 재고를 일괄 차감합니다. 주문 처리 시 사용됩니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "재고 차감 처리 결과",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class),
                examples = @ExampleObject(
                    name = "성공 예시",
                    value = """
                        {
                          \"code\": \"COMMON200\",
                          \"message\": \"성공입니다.\",
                          \"result\": {
                            \"status\": true,
                            \"productId\": [
                              \"11111111-1111-1111-1111-111111111111\",
                              \"22222222-2222-2222-2222-222222222222\"
                            ]
                          }
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "현재 재고 부족 등으로 실패",
            content = @Content(mediaType = "application/json")
        )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<StockAdjustmentResponseDto> decreaseStocks(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    );

    @Operation(
        summary = "재고 증가",
        description = "여러 상품의 재고를 일괄 증가합니다. 결제 취소 등 처리 시 사용됩니다."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "재고 증가 처리 결과",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class),
                examples = @ExampleObject(
                    name = "성공 예시",
                    value = """
                        {
                          \"code\": \"COMMON200\",
                          \"message\": \"성공입니다.\",
                          \"result\": {
                            \"status\": true,
                            \"productId\": [
                              \"11111111-1111-1111-1111-111111111111\",
                              \"22222222-2222-2222-2222-222222222222\"
                            ]
                          }
                        }
                    """
                )
            )
        )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<StockAdjustmentResponseDto> increaseStocks(
        @RequestBody @Valid StockAdjustmentRequestDto stockAdjustmentRequestDto
    );
}
