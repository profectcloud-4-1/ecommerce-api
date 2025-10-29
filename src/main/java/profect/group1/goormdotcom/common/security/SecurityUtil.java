package profect.group1.goormdotcom.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import profect.group1.goormdotcom.user.domain.enums.UserRole;

import java.util.List;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static List<UserRole> getCurrentUserRoles() {
        Authentication auth = getAuthentication();
        if (auth == null) return List.of();

        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", "")) // ex) ROLE_MASTER → MASTER
                .map(role -> {
                    try {
                        return UserRole.valueOf(role);
                    } catch (IllegalArgumentException e) {
                        return null; // 알 수 없는 권한 무시
                    }
                })
                .filter(role -> role != null)
                .toList();
    }

    public static boolean hasRole(UserRole role) {
        return getCurrentUserRoles().contains(role);
    }

    public static boolean isMaster() {
        return hasRole(UserRole.MASTER);
    }

    public static boolean isCustomer() {
        return hasRole(UserRole.CUSTOMER);

    }
}