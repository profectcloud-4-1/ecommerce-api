package profect.group1.goormdotcom.cart.controller.internal.v1;

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
import profect.group1.goormdotcom.cart.domain.Cart;
import profect.group1.goormdotcom.cart.service.CartService;
import profect.group1.goormdotcom.cart.service.CartServiceImpl;
import profect.group1.goormdotcom.user.controller.auth.LoginUser;

@Slf4j
@RestController
@RequestMapping("/internal/v1/carts")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CartInternalController implements CartInternalApiDocs {

	private final CartService cartService;

	@PostMapping
	public ApiResponse<UUID> createCart(
			@LoginUser UUID userId
	) {
		UUID cartId = cartService.createCart(userId);

		return ApiResponse.of(SuccessStatus._CREATED, cartId);
	}

}
