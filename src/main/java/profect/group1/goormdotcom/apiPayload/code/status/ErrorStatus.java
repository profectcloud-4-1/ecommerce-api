package profect.group1.goormdotcom.apiPayload.code.status;

import profect.group1.goormdotcom.apiPayload.code.BaseErrorCode;
import profect.group1.goormdotcom.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _CONFLICT(HttpStatus.CONFLICT, "COMMON409", "이미 존재하는 리소스입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "찾을 수 없는 요청입니다."),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청 파라미터입니다"),
    AUTH_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH400", "잘못된 비밀번호 형식입니다"),
    AUTH_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH409", "이미 존재하는 이메일입니다"),
    AUTH_NOT_EXISTS(HttpStatus.NOT_FOUND, "AUTH404", "존재하지 않는 사용자입니다"),
    INSUFFICIENT_ROLE(HttpStatus.FORBIDDEN, "AUTH403", "요청에 필요한 권한이 없습니다."),

    COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "존재하지 않는 공통 코드입니다"),
    COMMON_CODE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "COMMON409", "이미 존재하는 공통 코드입니다"),
    COMMON_CODE_INVALID_GROUP_NAME(HttpStatus.BAD_REQUEST, "COMMON400", "허용되지 않은 그룹이름입니다"),


    _DUPLICATE_PAYMENT_REQUEST(HttpStatus.CONFLICT, "PAYMENT409", "이미 처리 중인 결제 요청입니다."),
    _PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PAYMENT404", "존재하지 않는 결제 정보입니다."),
    _INVALID_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "PAYMENT400", "결제 금액이 일치하지 않습니다."),
    _INVALID_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "PAYMENT400", "현재 상태에서는 결제를 취소할 수 없습니다."),
    _ALREADY_CANCELED_REQUEST(HttpStatus.CONFLICT, "PAYMENT409", "이미 처리 중인 결제 요청입니다."),
    _PAYMENT_CANCEL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "PAYMENT500", "결제 취소 처리 중 오류가 발생했습니다."),
    _INVALID_CANCEL_AMOUNT(HttpStatus.BAD_REQUEST, "PAYMENT400", "취소 금액이 남은 결제 금액을 초과했습니다."),

    _INSUFFICIENT_STOCK_QUANTITY(HttpStatus.BAD_REQUEST, "STOCK400", "현재 재고가 요청한 수보다 적습니다."),
    _ADJUST_STOCK_FAILED(HttpStatus.CONFLICT, "STOCK409", "재고 변경에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}