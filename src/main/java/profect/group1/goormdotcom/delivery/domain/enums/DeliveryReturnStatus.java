
package profect.group1.goormdotcom.delivery.domain.enums;

public enum DeliveryReturnStatus {
    PENDING("DLV0010", "수거중"),
    IN_DELIVERY("DLV0011", "반송중"),
    FINISH("DLV0012", "반송완료");

    private final String code;
    private final String visibleLabel;

    DeliveryReturnStatus(String code, String visibleLabel) {
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