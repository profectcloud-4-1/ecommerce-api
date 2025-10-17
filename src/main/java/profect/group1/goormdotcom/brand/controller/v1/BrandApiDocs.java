package profect.group1.goormdotcom.brand.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.RequestBody;

import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandCreateRequestDto;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandEditRequestDto;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandListRequestDto;
import profect.group1.goormdotcom.brand.controller.dto.response.BrandCreateResponseDto;
import profect.group1.goormdotcom.brand.controller.dto.response.BrandListResponseDto;
import profect.group1.goormdotcom.brand.controller.dto.response.BrandReadResponseDto;

@Tag(name = "Brand", description = "브랜드 API")
public interface BrandApiDocs {

    @Operation(summary = "브랜드 생성", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<BrandCreateResponseDto> create(HttpServletRequest request, @RequestBody BrandCreateRequestDto body);

    @Operation(summary = "브랜드 수정", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<Void> edit(
        HttpServletRequest request,
        @Parameter(description = "대상 브랜드 ID") String brandId,
        @RequestBody BrandEditRequestDto body
    );

    @Operation(summary = "브랜드 삭제", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<Void> delete(
        HttpServletRequest request,
        @Parameter(description = "대상 브랜드 ID") String brandId
    );

    @Operation(summary = "브랜드 단건 조회")
    ApiResponse<BrandReadResponseDto> read(@Parameter(description = "대상 브랜드 ID") String brandId);

    @Operation(summary = "브랜드 목록 조회", description = "검색/필터/정렬 가능")
    ApiResponse<BrandListResponseDto> list(@ParameterObject BrandListRequestDto body);
}
