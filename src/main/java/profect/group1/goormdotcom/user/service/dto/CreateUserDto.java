package profect.group1.goormdotcom.user.service.dto;

import profect.group1.goormdotcom.user.domain.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUserDto {
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private String brandId;
}