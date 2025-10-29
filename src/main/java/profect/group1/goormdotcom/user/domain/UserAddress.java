package profect.group1.goormdotcom.user.domain;

import java.util.UUID;
import profect.group1.goormdotcom.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserAddress extends BaseEntity {
    private UUID id;
    private UUID userId;
    private LocalDateTime createdAt;
    private String address;
    private String addressDetail;
    private String zipcode;
    private String phone;
    private String name;
    private String deliveryMemo;
}