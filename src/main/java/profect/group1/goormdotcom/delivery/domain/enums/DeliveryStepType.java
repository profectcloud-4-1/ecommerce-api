package profect.group1.goormdotcom.delivery.domain.enums;

public enum DeliveryStepType {
    INIT("DLV0100", "배송요청 접수"),
    READY("DLV0101", "배송 준비완료"),
    HUB("DLV0102", "허브에 도착"),
    STARTED("DLV0103", "집배원이 배송 시작"),
    DONE("DLV0104", "배송완료");

    private final String code;
    private final String visibleLabel;

    DeliveryStepType(String code, String visibleLabel) {
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