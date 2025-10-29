package profect.group1.goormdotcom.category.controller.external.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.category.controller.external.v1.dto.CategoryRequestDto;
import profect.group1.goormdotcom.category.controller.external.v1.dto.CategoryResponseDto;
import profect.group1.goormdotcom.category.controller.external.v1.dto.CategoryTreeResponseDto;

@Tag(name = "Category", description = "카테고리 API")
public interface CategoryApiDocs {

    @Operation(summary = "카테고리 트리 조회 (GET /api/v1/category)", description = "루트(ROOT)부터 전체 카테고리 트리를 반환합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(name = "success", value = "{\n  \"code\": \"COMMON200\",\n  \"message\": \"성공입니다.\",\n  \"result\": {\n    \"id\": \"00000000-0000-0000-0000-000000000000\",\n    \"name\": \"ROOT\",\n    \"children\": []\n  }\n}")
        )
    )
    ApiResponse<CategoryTreeResponseDto> getCategory();

    @Operation(
        summary = "하위 카테고리 조회 (GET /api/v1/category/{categoryId})",
        description = "지정한 categoryId의 바로 아래(1-depth) 자식 카테고리만 포함한 트리를 반환합니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(name = "success", value = "{\n  \"code\": \"COMMON200\",\n  \"message\": \"성공입니다.\",\n  \"result\": {\n    \"id\": \"11111111-1111-1111-1111-111111111111\",\n    \"name\": \"상의\",\n    \"children\": [\n      { \n        \"id\": \"22222222-2222-2222-2222-222222222222\", \n        \"name\": \"티셔츠\", \n        \"children\": [] \n      },\n      { \n        \"id\": \"33333333-3333-3333-3333-333333333333\", \n        \"name\": \"셔츠\", \n        \"children\": [] \n      }\n    ]\n  }\n}")
        )
    )
    ApiResponse<CategoryTreeResponseDto> getChildCategory(@Parameter(description = "부모 카테고리 ID") UUID categoryId);

    @Operation(summary = "카테고리 생성 (POST /api/v1/category)")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<UUID> createCategory(@RequestBody CategoryRequestDto body);

    @Operation(summary = "카테고리 삭제 (DELETE /api/v1/category/{categoryId})")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<UUID> deleteCategory(@Parameter(description = "카테고리 ID") UUID categoryId);

    @Operation(summary = "카테고리 수정 (PUT /api/v1/category/{categoryId})")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
    )
    ApiResponse<CategoryResponseDto> updateCategory(
        @Parameter(description = "카테고리 ID") UUID categoryId,
        @RequestBody CategoryRequestDto body
    );
}
