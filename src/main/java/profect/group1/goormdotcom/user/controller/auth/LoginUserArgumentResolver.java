package profect.group1.goormdotcom.user.controller.auth;

import java.util.UUID;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUser.class)
				&& parameter.getParameterType().equals(UUID.class);
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter,
			final ModelAndViewContainer mavContainer,
			final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
			throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			throw new IllegalStateException("No authentication found");
		}

		String userId = (String) authentication.getPrincipal();
		return UUID.fromString(userId);
	}
}
