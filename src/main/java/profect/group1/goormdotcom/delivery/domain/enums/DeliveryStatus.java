package profect.group1.goormdotcom.delivery.domain.enums;

public enum DeliveryStatus {
    CREATED("DLV0000", "배송 생성"),
    PENDING("DLV0001", "발송 대기"),
    IN_DELIVERY("DLV0002", "배송 중"),
    FINISH("DLV0003", "배송 완료"),
    CANCELLED("DLV0004", "배송 취소");

    private final String code;
    private final String visibleLabel;

    DeliveryStatus(String code, String visibleLabel) {
        this.code = code;
        this.visibleLabel = visibleLabel;
    }

    public String getCode() {
        return code;
    }
    
    public String getVisibleLabel() {
        return visibleLabel;
    }
}