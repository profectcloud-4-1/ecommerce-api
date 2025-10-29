package profect.group1.goormdotcom.delivery.domain;

import java.util.UUID;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeliveryStepHistory {

	private String stepType;
	private LocalDateTime createdAt;

}