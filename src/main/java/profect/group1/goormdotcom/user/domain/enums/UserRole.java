package profect.group1.goormdotcom.user.domain.enums;

import java.util.Arrays;

public enum UserRole {
    MASTER("USR0001", "관리자"),
    CUSTOMER("USR0002", "구매자");

    private final String code;
    private final String visibleLabel;

    UserRole(String code, String visibleLabel) {
        this.code = code;
        this.visibleLabel = visibleLabel;
    }

    public static UserRole fromCode(String code) {
        return Arrays.stream(UserRole.values())
            .filter(role -> role.getCode().equals(code))
            .findFirst()
            .orElse(null);
    }

    public String getCode() {
        return this.code;
    }

    public String getVisibleLabel() {
        return this.visibleLabel;
    }
}
