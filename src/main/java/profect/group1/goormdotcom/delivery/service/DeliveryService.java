package profect.group1.goormdotcom.delivery.service;

import java.util.UUID;
import java.util.List;

import profect.group1.goormdotcom.delivery.domain.Delivery;
import profect.group1.goormdotcom.delivery.domain.DeliveryReturn;
import profect.group1.goormdotcom.delivery.domain.DeliveryAddress;
import profect.group1.goormdotcom.delivery.controller.external.v1.dto.request.CreateAddressRequestDto;

public interface DeliveryService {
	/**
	 * 반송 가능 여부 확인
	 * @return -1: 존재하지 않는 order id, 0: 반송불가(현재 배송중), 1: 즉시취소 가능(배송 시작 전이기 때문), 2: 반송 프로세스를 거쳐 반송 가능(배송 완료됨)
	 */
	public Integer canReturn(UUID orderId);
	/** 
	 * 배송 취소 (즉시취소 가능한 경우만 사용, 불가능한 경우 예외 발생)
	 * @return 정상처리여부
	 */
	public boolean cancel(UUID orderId);

	/** 
	 * 배송 시작
	 * @param orderId 주문 ID
	 * @param customerId 구매자 ID
	 * @param address 배송지 주소
	 * @param addressDetail 배송지 상세주소
	 * @param zipcode 배송지 우편번호
	 * @param phone 배송지 전화번호
	 * @param name 수취인 이름
	 * @param deliveryMemo 배송 메모
	 * @return 배송 정보
	 */
	public Delivery startDelivery(UUID orderId, UUID customerId, String address, String addressDetail, String zipcode, String phone, String name, String deliveryMemo);

	/** 
	 * 배송 반송 (배송 완료된 경우만 사용, 배송완료가 아닌 경우 예외 발생)
	 * @param orderId 주문 ID
	 * @return 정상처리여부
	 */
	public DeliveryReturn returnDelivery(UUID orderId);

    // Goorm address (MASTER)
    public DeliveryAddress getGoormAddress();
    public DeliveryAddress createGoormAddress(CreateAddressRequestDto body);
	public DeliveryAddress updateGoormAddress(CreateAddressRequestDto body);

	public Delivery getDeliveryByOrderId(UUID orderId);
}
