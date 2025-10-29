package profect.group1.goormdotcom.delivery.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryAddress {

	private final UUID id;
	private final String address;
	private final String addressDetail;
	private final String zipcode;
	private final String phone;
	private final String name;
	private final String deliveryMemo;
}