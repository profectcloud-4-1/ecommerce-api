package profect.group1.goormdotcom.user.domain;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;
import profect.group1.goormdotcom.user.domain.enums.UserRole;
import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private UUID id;
    private LocalDateTime createdAt;
    private String email;
    private String name;
    private UserRole role;
    private String brandId;
}
