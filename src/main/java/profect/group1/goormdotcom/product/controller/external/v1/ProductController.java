package profect.group1.goormdotcom.product.controller.external.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.SuccessStatus;
import profect.group1.goormdotcom.product.controller.external.v1.dto.DeleteProductRequestDto;
import profect.group1.goormdotcom.product.controller.external.v1.dto.ProductRequestDto;
import profect.group1.goormdotcom.product.controller.external.v1.dto.ProductResponseDto;
import profect.group1.goormdotcom.product.controller.external.v1.dto.UpdateProductRequestDto;
import profect.group1.goormdotcom.product.controller.external.v1.mapper.ProductDtoMapper;
import profect.group1.goormdotcom.product.domain.Product;
import profect.group1.goormdotcom.product.service.ProductService;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductController implements ProductApiDocs {
    private final ProductService productService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<UUID> registerProduct(
        @RequestBody @Valid ProductRequestDto request
    ) {
        // 상품 ID
        UUID productId = productService.createProduct(
            request.brandId(), 
            request.categoryId(), 
            request.name(), 
            request.price(), 
            request.stockQuantity(),
            request.description(),
            request.imageIds()
        );
        
        return ApiResponse.of(SuccessStatus._OK, productId);
    }
    
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponseDto> getProduct(
        @PathVariable(value = "productId") UUID productId
    ) {
        Product product = productService.getProduct(productId);
        return ApiResponse.of(SuccessStatus._OK, ProductDtoMapper.toProductResponseDto(product));
    }
    
    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<ProductResponseDto> updateProduct(
        @PathVariable(value = "productId") UUID productId, 
        @RequestBody @Valid UpdateProductRequestDto request
    ) {
        Product product = productService.updateProduct(
            productId,
            request.brandId(),
            request.categoryId(),
            request.name(),
            request.price(),
            request.description(),
            request.imageIds()
        );
        
        return ApiResponse.of(SuccessStatus._OK, ProductDtoMapper.toProductResponseDto(product));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<UUID> deleteProduct(
        @PathVariable(value = "productId") UUID productId,
        @RequestBody @Valid UUID brandId
    )  {
        productService.deleteProduct(productId, brandId);
        return ApiResponse.of(SuccessStatus._OK, productId);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<List<UUID>> deleteProducts(
        @RequestBody DeleteProductRequestDto request
    )  {
        productService.deleteProducts(request.productIds(), request.brandId());
        return ApiResponse.of(SuccessStatus._OK, request.productIds());
    }
    
    // 이미지 삭제 요청 (soft delete 처리)
    @DeleteMapping("/image/{imageId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<UUID> deleteProductImage(
        @PathVariable(value = "imageId") UUID imageId   
    ) {
        
        productService.deleteProductImage(imageId);
        return ApiResponse.of(SuccessStatus._OK, imageId);
    }
}
