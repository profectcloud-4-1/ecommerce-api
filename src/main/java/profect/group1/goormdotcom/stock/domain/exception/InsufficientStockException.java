package profect.group1.goormdotcom.stock.domain.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("현재 재고가 요청한 수량보다 부족합니다.");
    }
}
