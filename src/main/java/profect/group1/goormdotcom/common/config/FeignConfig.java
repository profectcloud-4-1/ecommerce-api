package profect.group1.goormdotcom.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FeignConfig implements RequestInterceptor {
	
	// NOTE: 외부에서 들어온 요청을 다시 내부의 서비스에 전달하는 것이니, 외부로부터 전달 받은 Authorization 헤더를 그대로 전파함.
	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attrs == null) return;
		String auth = attrs.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
		if (auth != null && !auth.isBlank()) {
			template.header(HttpHeaders.AUTHORIZATION, auth);
		}
	}

	@Bean
	public feign.Retryer feignRetryer() {
		// (period, maxPeriod, maxAttempts)
		return new feign.Retryer.Default(200, 1000, 3);
	}

}