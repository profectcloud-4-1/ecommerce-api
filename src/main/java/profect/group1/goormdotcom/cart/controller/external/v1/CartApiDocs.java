package profect.group1.goormdotcom.cart.controller.external.v1;

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

@Tag(name = "장바구니", description = "장바구니 관리 API")
public interface CartApiDocs {

	@Operation(
			summary = "장바구니 조회",
			description = "현재 사용자의 장바구니 정보를 조회합니다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "장바구니 조회 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
											    "code": "200",
											    "message": "요청에 성공하였습니다.",
											    "result": {
											        "id": "123e4567-e89b-12d3-a456-426614174000",
											        "customerId": "123e4567-e89b-12d3-a456-426614174001",
											        "totalQuantity": 5,
											        "totalPrice": 25000,
											        "items": [
											            {
											                "id": "123e4567-e89b-12d3-a456-426614174002",
											                "cartId": "123e4567-e89b-12d3-a456-426614174000",
											                "productId": "123e4567-e89b-12d3-a456-426614174003",
											                "quantity": 2,
											                "price": 10000
											            }
											        ]
											    }
											}
											"""
							)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "장바구니를 찾을 수 없음",
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(
									name = "실패 예시",
									value = """
											{
											    "code": "404",
											    "message": "장바구니를 찾을 수 없습니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<CartResponseDto> getCart(
			@LoginUser UUID userId
	);

	@Operation(
			summary = "장바구니에 아이템 추가",
			description = "장바구니에 새로운 아이템을 추가하거나 기존 아이템의 수량을 증가시킵니다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "아이템 추가 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
											    "code": "200",
											    "message": "요청에 성공하였습니다.",
											    "result": {
											        "id": "123e4567-e89b-12d3-a456-426614174000",
											        "customerId": "123e4567-e89b-12d3-a456-426614174001",
											        "totalQuantity": 7,
											        "totalPrice": 35000,
											        "items": [
											            {
											                "id": "123e4567-e89b-12d3-a456-426614174002",
											                "cartId": "123e4567-e89b-12d3-a456-426614174000",
											                "productId": "123e4567-e89b-12d3-a456-426614174003",
											                "quantity": 4,
											                "price": 10000
											            }
											        ]
											    }
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
											    "message": "수량은 양수여야 합니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<CartResponseDto> addItemToCart(
			@Parameter(description = "장바구니에 추가할 아이템 정보", required = true)
			@RequestBody @Valid AddCartItemRequestDto request,
			@LoginUser UUID userId
	);

	@Operation(
			summary = "장바구니 아이템 수량 수정",
			description = "장바구니의 특정 아이템 수량을 수정합니다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "아이템 수량 수정 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
											    "code": "200",
											    "message": "요청에 성공하였습니다.",
											    "result": {
											        "id": "123e4567-e89b-12d3-a456-426614174000",
											        "customerId": "123e4567-e89b-12d3-a456-426614174001",
											        "totalQuantity": 3,
											        "totalPrice": 15000,
											        "items": [
											            {
											                "id": "123e4567-e89b-12d3-a456-426614174002",
											                "cartId": "123e4567-e89b-12d3-a456-426614174000",
											                "productId": "123e4567-e89b-12d3-a456-426614174003",
											                "quantity": 3,
											                "price": 10000
											            }
											        ]
											    }
											}
											"""
							)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "잘못된 요청 데이터 또는 아이템을 찾을 수 없음",
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(
									name = "실패 예시",
									value = """
											{
											    "code": "400",
											    "message": "아이템을 찾을 수 없습니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<CartResponseDto> updateItemToCart(
			@Parameter(description = "수정할 장바구니 아이템 ID", required = true)
			@PathVariable(value = "cartItemId") UUID cartItemId,
			@Parameter(description = "수정할 수량 정보", required = true)
			@RequestBody @Valid UpdateCartItemRequestDto request,
			@LoginUser UUID userId
	);

	@Operation(
			summary = "장바구니 아이템 삭제",
			description = "장바구니에서 특정 아이템을 삭제합니다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "아이템 삭제 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
											    "code": "200",
											    "message": "요청에 성공하였습니다.",
											    "result": {
											        "id": "123e4567-e89b-12d3-a456-426614174000",
											        "customerId": "123e4567-e89b-12d3-a456-426614174001",
											        "totalQuantity": 0,
											        "totalPrice": 0,
											        "items": []
											    }
											}
											"""
							)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "아이템을 찾을 수 없음",
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(
									name = "실패 예시",
									value = """
											{
											    "code": "404",
											    "message": "아이템을 찾을 수 없습니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<CartResponseDto> deleteItemFromCart(
			@Parameter(description = "삭제할 장바구니 아이템 ID", required = true)
			@PathVariable(value = "cartItemId") UUID cartItemId,
			@LoginUser UUID userId
	);

	@Operation(
			summary = "장바구니 아이템 일괄 삭제",
			description = "장바구니에서 여러 아이템을 한 번에 삭제합니다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "아이템 일괄 삭제 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
											    "code": "200",
											    "message": "요청에 성공하였습니다.",
											    "result": {
											        "id": "123e4567-e89b-12d3-a456-426614174000",
											        "customerId": "123e4567-e89b-12d3-a456-426614174001",
											        "totalQuantity": 2,
											        "totalPrice": 10000,
											        "items": [
											            {
											                "id": "123e4567-e89b-12d3-a456-426614174004",
											                "cartId": "123e4567-e89b-12d3-a456-426614174000",
											                "productId": "123e4567-e89b-12d3-a456-426614174005",
											                "quantity": 2,
											                "price": 5000
											            }
											        ]
											    }
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
											    "message": "삭제할 아이템이 최소 하나는 있어야 합니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<CartResponseDto> deleteBulkItemFromCart(
			@Parameter(description = "삭제할 장바구니 아이템 ID 목록", required = true)
			@RequestBody @Valid DeleteBulkCartItemRequestDto request,
			@LoginUser UUID userId
	);

	@Operation(
			summary = "장바구니 전체 비우기",
			description = "장바구니의 모든 아이템을 삭제합니다."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "장바구니 전체 삭제 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ApiResponse.class),
							examples = @ExampleObject(
									name = "성공 예시",
									value = """
											{
											    "code": "200",
											    "message": "요청에 성공하였습니다.",
											    "result": {
											        "id": "123e4567-e89b-12d3-a456-426614174000",
											        "customerId": "123e4567-e89b-12d3-a456-426614174001",
											        "totalQuantity": 0,
											        "totalPrice": 0,
											        "items": []
											    }
											}
											"""
							)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "장바구니를 찾을 수 없음",
					content = @Content(
							mediaType = "application/json",
							examples = @ExampleObject(
									name = "실패 예시",
									value = """
											{
											    "code": "404",
											    "message": "장바구니를 찾을 수 없습니다."
											}
											"""
							)
					)
			)
	})
	profect.group1.goormdotcom.apiPayload.ApiResponse<CartResponseDto> deleteAllItemsFromCart(
			@LoginUser UUID userId
	);
}
