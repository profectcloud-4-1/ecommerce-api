package profect.group1.goormdotcom.order.repository.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;


@Entity
@Table(name = "p_common_code")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class CommonCodeEntity {
    
    @Id
    private String code;

    @Column(name = "code_group", nullable = false, length = 100)
    private String codeGroup;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, length = 100)
    private String label;
}
