package profect.group1.goormdotcom.product.controller.external.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.product.controller.external.v1.dto.DeleteProductRequestDto;
import profect.group1.goormdotcom.product.controller.external.v1.dto.ProductRequestDto;
import profect.group1.goormdotcom.product.controller.external.v1.dto.ProductResponseDto;
import profect.group1.goormdotcom.product.controller.external.v1.dto.UpdateProductRequestDto;

@Tag(name = "Product", description = "상품 API")
public interface ProductApiDocs {

    @Operation(summary = "상품 등록", description = "SELLER 전용", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<UUID> registerProduct(@RequestBody ProductRequestDto request);

    @Operation(summary = "상품 상세 조회")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<ProductResponseDto> getProduct(@Parameter(description = "상품 ID") UUID productId);

    

    @Operation(summary = "상품 수정", description = "SELLER 전용", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<ProductResponseDto> updateProduct(
        @Parameter(description = "상품 ID") UUID productId,
        @RequestBody UpdateProductRequestDto request
    );

    @Operation(summary = "상품 삭제", description = "SELLER 전용", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<UUID> deleteProduct(@Parameter(description = "상품 ID") UUID productId, @RequestBody @Valid UUID brandId);

    @Operation(summary = "상품 일괄 삭제", description = "SELLER 전용", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<List<UUID>> deleteProducts(@RequestBody DeleteProductRequestDto request);
}
