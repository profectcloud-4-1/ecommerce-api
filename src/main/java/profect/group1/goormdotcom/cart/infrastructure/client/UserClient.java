package profect.group1.goormdotcom.cart.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import profect.group1.goormdotcom.cart.infrastructure.client.dto.UserResponseDto;

@FeignClient(
		name = "user-service",
		fallback = UserClientFallBack.class
)
public interface UserClient {

	@GetMapping("/api/users")
	UserResponseDto something();
}
