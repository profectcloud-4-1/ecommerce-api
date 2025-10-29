package profect.group1.goormdotcom.stock.controller.external.v1;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import profect.group1.goormdotcom.stock.controller.external.v1.dto.StockRequestDto;
import profect.group1.goormdotcom.stock.controller.external.v1.dto.StockResponseDto;

@Tag(name = "재고", description = "재고 관리 API")
public interface StockApiDocs {

    @Operation(
        summary = "재고 등록",
        description = "상품의 재고를 등록합니다. (SELLER 전용)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "재고 등록 성공",
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
                            \"productId\": \"123e4567-e89b-12d3-a456-426614174000\",
                            \"stockQuantity\": 100,
                            \"updatedAt\": \"2024-10-24T12:34:56\"
                          }
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "이미 재고가 존재하거나 잘못된 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "실패 예시",
                    value = """
                        {
                          \"code\": \"COMMON400\",
                          \"message\": \"잘못된 요청입니다.\"
                        }
                    """
                )
            )
        )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<StockResponseDto> registerStock(
        @RequestBody StockRequestDto stockRequestDto
    );

    @Operation(
        summary = "재고 수정",
        description = "특정 상품의 재고 수량을 수정합니다. (SELLER 전용)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "재고 수정 성공",
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
                            \"productId\": \"123e4567-e89b-12d3-a456-426614174000\",
                            \"stockQuantity\": 50,
                            \"updatedAt\": \"2024-10-24T12:34:56\"
                          }
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "상품을 찾을 수 없음",
            content = @Content(mediaType = "application/json")
        )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<StockResponseDto> updateStock(
        @Parameter(description = "상품 ID", required = true)
        @PathVariable(value = "productId") UUID productId,
        @Parameter(description = "새 재고 수량 (정수)")
        @RequestBody int stockQuantity
    );

    @Operation(
        summary = "재고 조회",
        description = "특정 상품의 재고 정보를 조회합니다. (SELLER 전용)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "재고 조회 성공",
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
                            \"productId\": \"123e4567-e89b-12d3-a456-426614174000\",
                            \"stockQuantity\": 100,
                            \"updatedAt\": \"2024-10-24T12:34:56\"
                          }
                        }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "상품을 찾을 수 없음",
            content = @Content(mediaType = "application/json")
        )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<StockResponseDto> getStock(
        @Parameter(description = "상품 ID", required = true)
        @PathVariable(value = "productId") UUID productId
    );

    @Operation(
        summary = "재고 삭제",
        description = "특정 상품의 재고 정보를 삭제합니다. (SELLER 전용)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "재고 삭제 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class),
                examples = @ExampleObject(
                    name = "성공 예시",
                    value = """
                        {
                          \"code\": \"COMMON200\",
                          \"message\": \"성공입니다.\",
                          \"result\": \"123e4567-e89b-12d3-a456-426614174000\"
                        }
                    """
                )
            )
        )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<UUID> deleteStock(
        @Parameter(description = "상품 ID", required = true)
        @PathVariable(value = "productId") UUID productId
    );

}
