package profect.group1.goormdotcom.brand.domain;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;
import java.time.LocalDateTime;

@Getter
@Builder
public class Brand {
    private UUID id;
    private LocalDateTime createdAt;
    private String name;
    private String description;
    private String website;
}
