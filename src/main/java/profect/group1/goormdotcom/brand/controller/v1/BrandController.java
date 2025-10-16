package profect.group1.goormdotcom.brand.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandCreateRequestDto;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandEditRequestDto;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandListRequestDto;
import profect.group1.goormdotcom.brand.controller.dto.response.BrandCreateResponseDto;
import profect.group1.goormdotcom.brand.controller.dto.response.BrandListResponseDto;
import profect.group1.goormdotcom.brand.controller.dto.response.BrandReadResponseDto;
import profect.group1.goormdotcom.brand.service.BrandService;
import profect.group1.goormdotcom.brand.controller.mapper.BrandDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.brand.domain.Brand;
import org.springframework.security.access.prepost.PreAuthorize;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService service;
    private final BrandDtoMapper dtoMapper;

    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<BrandCreateResponseDto> create(HttpServletRequest request, @Valid @RequestBody BrandCreateRequestDto body) {
        Claims claims = (Claims) request.getAttribute("jwtClaims");
        Brand brand = service.create(body.getName(), body.getDescription(), body.getWebsite(), claims.getSubject());
        return ApiResponse.onSuccess(BrandCreateResponseDto.of(brand.getId().toString()));
    }

    @PatchMapping("/{brandId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<Void> edit(HttpServletRequest request, @PathVariable String brandId, @Valid @RequestBody BrandEditRequestDto body) {
        Claims claims = (Claims) request.getAttribute("jwtClaims");
        try {
            service.edit(brandId, body.getName(), body.getDescription(), body.getWebsite(), claims.getSubject());
            return ApiResponse.onSuccess(null);
        } catch(Exception e) {
            String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            switch (e.getMessage()) {
                case "Brand not found":
                    code = "400";
                    message = "존재하지 않는 브랜드입니다.";
                    break;
            }
            return ApiResponse.onFailure(code, message, null);
        }
    }

    @DeleteMapping("/{brandId}")
    @PreAuthorize("hasRole('MASTER')")
    public ApiResponse<Void> delete(HttpServletRequest request, @PathVariable String brandId) {
        Claims claims = (Claims) request.getAttribute("jwtClaims");

        try {
            service.delete(brandId, claims.getSubject());
            return ApiResponse.onSuccess(null);
        } catch(Exception e) {
            String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            switch (e.getMessage()) {
                case "Brand not found":
                    code = "400";
                    message = "존재하지 않는 브랜드입니다.";
                    break;
            }
            return ApiResponse.onFailure(code, message, null);
        }
    }

    @GetMapping("/{brandId}")
    public ApiResponse<BrandReadResponseDto> read(@PathVariable String brandId) {
        try {
        Brand brand = service.findById(brandId);
        return ApiResponse.onSuccess(BrandReadResponseDto.of(brand));
        } catch(Exception e) {
            String code = ErrorStatus._INTERNAL_SERVER_ERROR.getCode();
            String message = ErrorStatus._INTERNAL_SERVER_ERROR.getMessage();
            switch (e.getMessage()) {
                case "Brand not found":
                    code = "400";
                    message = "존재하지 않는 브랜드입니다.";
                    break;
            }
            return ApiResponse.onFailure(code, message, null);
        }
    }

    @GetMapping
    public ApiResponse<BrandListResponseDto> list(BrandListRequestDto body) {
        List<Brand> brands = service.searchBrands(body);
        return ApiResponse.onSuccess(BrandListResponseDto.of(brands));
    }
}
