package profect.group1.goormdotcom.delivery.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import profect.group1.goormdotcom.delivery.domain.DeliveryAddress;
import profect.group1.goormdotcom.delivery.domain.DeliveryStepHistory;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryReturn {

	private UUID id;
	private UUID deliveryId;
	private String status;
	private String trackingNumber;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

    private DeliveryAddress senderAddress;
	private DeliveryAddress receiverAddress;
	private List<DeliveryStepHistory> deliveryStepHistories;
	
}
