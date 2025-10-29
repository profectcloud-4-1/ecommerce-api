package profect.group1.goormdotcom.cart.controller.internal.v1;

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
import profect.group1.goormdotcom.cart.controller.external.v1.dto.CartResponseDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.request.AddCartItemRequestDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.request.DeleteBulkCartItemRequestDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.request.UpdateCartItemRequestDto;
import profect.group1.goormdotcom.user.controller.auth.LoginUser;

@Tag(name = "Cart (internal)", description = "장바구니 API (내부서비스간 통신용)")
public interface CartInternalApiDocs {

	@Operation(
			summary = "장바구니 생성",
			description = "회원가입 한 사용자의 장바구니를 생성한다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "장바구니 생성 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
												"code": "201",
												"message": "장바구니 생성에 성공하였습니다.",
												"result": "123e4567-e89b-12d3-a456-426614174000"
											}
											"""
							)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "잘못된 요청 데이터",
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(
									name = "실패 예시",
									value = """
											{
											    "code": "400",
											    "message": "이미 장바구니가 있습니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<UUID> createCart(
			@LoginUser UUID userId
	);

}
