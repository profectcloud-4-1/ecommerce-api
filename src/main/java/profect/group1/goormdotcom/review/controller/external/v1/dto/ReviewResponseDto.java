package profect.group1.goormdotcom.review.controller.external.v1.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private UUID reviewId;
    private int rating;
    private UUID userId;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    // POST/PUT/GET(항목별로) 모두 이 Response 재사용

}
