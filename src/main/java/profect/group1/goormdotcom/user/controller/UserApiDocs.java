package profect.group1.goormdotcom.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;
import profect.group1.goormdotcom.user.controller.auth.LoginUser;

import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.user.controller.dto.request.EditRequestDto;
import profect.group1.goormdotcom.user.controller.dto.request.ListRequestDto;
import profect.group1.goormdotcom.user.controller.dto.request.LoginRequestDto;
import profect.group1.goormdotcom.user.controller.dto.request.RegisterRequestDto;
import profect.group1.goormdotcom.user.controller.dto.response.ListResponseDto;
import profect.group1.goormdotcom.user.controller.dto.response.LoginResponseDto;
import profect.group1.goormdotcom.user.controller.dto.response.MeResponseDto;
import profect.group1.goormdotcom.user.controller.dto.response.RegisterResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@Tag(name = "User", description = "사용자 API")
public interface UserApiDocs {

	@Operation(summary = "회원가입")
	ApiResponse<RegisterResponseDto> register(@RequestBody RegisterRequestDto body);

	@Operation(summary = "로그인")
	ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto body);

	@Operation(summary = "회원 탈퇴", security = { @SecurityRequirement(name = "bearerAuth") })
	@io.swagger.v3.oas.annotations.responses.ApiResponse(
		responseCode = "200",
		description = "성공",
		content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
	)
	ApiResponse<Object> leave(@LoginUser UUID userId);

	@Operation(summary = "내 정보 수정", security = { @SecurityRequirement(name = "bearerAuth") })
	@io.swagger.v3.oas.annotations.responses.ApiResponse(
		responseCode = "200",
		description = "성공",
		content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
	)
	ApiResponse<Object> edit(@LoginUser UUID userId, @RequestBody EditRequestDto body);

	@Operation(summary = "사용자 삭제", description = "MASTER only", security = { @SecurityRequirement(name = "bearerAuth") })
	@io.swagger.v3.oas.annotations.responses.ApiResponse(
		responseCode = "200",
		description = "성공",
		content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "success", value = "{\"code\":\"COMMON200\",\"message\":\"성공입니다.\"}"))
	)
	ApiResponse<Object> delete(@PathVariable UUID userId);

    @Operation(summary = "내 정보 조회", security = { @SecurityRequirement(name = "bearerAuth") })
	ApiResponse<MeResponseDto> me(@LoginUser UUID userId);

	@Operation(summary = "사용자 목록 조회", description = "검색/필터/정렬 가능", security = { @SecurityRequirement(name = "bearerAuth") })
	ApiResponse<ListResponseDto> users(@ParameterObject ListRequestDto body);
}
