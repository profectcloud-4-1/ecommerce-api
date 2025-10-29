package profect.group1.goormdotcom.cart.controller.external.v1;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.SuccessStatus;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.CartResponseDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.request.AddCartItemRequestDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.request.DeleteBulkCartItemRequestDto;
import profect.group1.goormdotcom.cart.controller.external.v1.dto.request.UpdateCartItemRequestDto;
import profect.group1.goormdotcom.cart.controller.external.v1.mapper.CartDtoMapper;
import profect.group1.goormdotcom.cart.domain.Cart;
import profect.group1.goormdotcom.cart.service.CartService;
import profect.group1.goormdotcom.cart.service.CartServiceImpl;
import profect.group1.goormdotcom.user.controller.auth.LoginUser;

@Slf4j
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CartController implements CartApiDocs {

	private final CartService cartService;

	@GetMapping
	public ApiResponse<CartResponseDto> getCart(
			@LoginUser UUID userId
	) {
		Cart cart = cartService.getCart(userId);

		return ApiResponse.of(SuccessStatus._OK, CartDtoMapper.toCartDto(cart));
	}
	
	@PostMapping("/items")
	public ApiResponse<CartResponseDto> addItemToCart(
			@RequestBody @Valid AddCartItemRequestDto request,
			@LoginUser UUID userId
	) {
		Cart cart = cartService.addCartItem(userId,
				request.getProductId(),
				request.getQuantity(),
				request.getPrice()
		);

		return ApiResponse.of(SuccessStatus._OK, CartDtoMapper.toCartDto(cart));
	}

	@PutMapping("/items/{cartItemId}")
	public ApiResponse<CartResponseDto> updateItemToCart(
			@PathVariable(value = "cartItemId") UUID cartItemId,
			@RequestBody @Valid UpdateCartItemRequestDto request,
			@LoginUser UUID userId
	) {
		Cart cart = cartService.updateCartItem(
				userId,
				cartItemId,
				request.getQuantity()
		);

		return ApiResponse.of(SuccessStatus._OK, CartDtoMapper.toCartDto(cart));
	}

	@PutMapping("/items/bulk-delete")
	public ApiResponse<CartResponseDto> deleteBulkItemFromCart(
			@RequestBody @Valid DeleteBulkCartItemRequestDto request,
			@LoginUser UUID userId
	) {
		Cart cart = cartService.removeBulkItem(userId, request.getCartItemIds());

		return ApiResponse.of(SuccessStatus._OK, CartDtoMapper.toCartDto(cart));
	}

	@DeleteMapping("/items/{cartItemId}")
	public ApiResponse<CartResponseDto> deleteItemFromCart(
			@PathVariable(value = "cartItemId") UUID cartItemId,
			@LoginUser UUID userId
	) {
		Cart cart = cartService.removeCartItem(userId, cartItemId);

		return ApiResponse.of(SuccessStatus._OK, CartDtoMapper.toCartDto(cart));
	}

	@DeleteMapping("/items")
	public ApiResponse<CartResponseDto> deleteAllItemsFromCart(
			@LoginUser UUID userId
	) {
		Cart cart = cartService.clearCart(userId);

		return ApiResponse.of(SuccessStatus._OK, CartDtoMapper.toCartDto(cart));
	}
}
