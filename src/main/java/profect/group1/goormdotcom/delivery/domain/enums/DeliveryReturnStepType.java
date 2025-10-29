package profect.group1.goormdotcom.delivery.domain.enums;

public enum DeliveryReturnStepType {
    INIT("DLV0110", "반송요청 접수"),
    STARTED("DLV0111", "집배원이 수거 시작"),
    HUB("DLV0112", "허브에 도착"),
    STARTED_HUB("DLV0113", "집배원이 반송 시작"),
    DONE("DLV0114", "반송완료");

    private final String code;
    private final String visibleLabel;

    DeliveryReturnStepType(String code, String visibleLabel) {
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