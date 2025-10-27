package profect.group1.goormdotcom.cart.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.cart.infrastructure.client.dto.UserResponseDto;

@Slf4j
@Component
public class UserClientFallBack implements UserClient {


	@Override
	public UserResponseDto something() {
		log.info("[Feign-Fallback] User-service request failed...");

		return new UserResponseDto();
	}
}
