package profect.group1.goormdotcom.apiPayload.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.BaseErrorCode;
import profect.group1.goormdotcom.apiPayload.code.ErrorReasonDTO;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    // 파라미터 유효성 검사 실패 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        BaseErrorCode errorCode = getErrorCodeByName(errorMessage);
        return handleExceptionInternalConstraint(e, errorCode, HttpHeaders.EMPTY, request);
    }

    // @RequestBody 필드 유효성 검증 실패 처리
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
            errors.merge(fieldName, errorMessage, (existing, replacement) -> existing + ", " + replacement);
        });

        BaseErrorCode errorCode = getErrorCodeByName("_BAD_REQUEST");
        return handleExceptionInternalArgs(e, HttpHeaders.EMPTY, errorCode, request, errors);
    }

    //클라이언트 요청 본문의 JSON 파싱 실패 처리
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        BaseErrorCode errorCode = getErrorCodeByName("_BAD_REQUEST");
        String errorPoint = "요청 형식이 올바르지 않습니다. JSON 형식을 확인해주세요.";

        Throwable cause = ex.getMostSpecificCause();

        // Enum 값 잘못된 경우 상세 메시지 생성
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException inv) {
            String path = inv.getPath().stream()
                    .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : "[" + ref.getIndex() + "]")
                    .collect(Collectors.joining("."));
            Class<?> target = inv.getTargetType();
            Object invalid = inv.getValue();

            if (target.isEnum()) {
                String allowed = Arrays.stream(target.getEnumConstants())
                        .map(e -> ((Enum<?>) e).name())
                        .collect(Collectors.joining(", "));
                errorPoint = String.format(
                        "필드 '%s'의 값 '%s'는 잘못된 Enum 값입니다. 허용 값: [%s]",
                        path, invalid, allowed
                );
            }

        }

        return handleExceptionInternalFalse(
                ex,
                errorCode,
                headers,
                errorCode.getReasonHttpStatus().getHttpStatus(),
                request,
                errorPoint
        );
    }

    // 커스텀 GeneralException 응답 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO reason = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException, reason, null, request);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<Object> handleBadRequestParameter(MethodArgumentTypeMismatchException ex, WebRequest request) {

        BaseErrorCode errorCode = ErrorStatus.INVALID_REQUEST_PARAMETER;

        String detailMessage = String.format("파라미터 '%s' 값 '%s'은(는) 올바른 형식이 아닙니다.",
                ex.getName(), ex.getValue());

        ApiResponse<Object> body = ApiResponse.onFailure(
                errorCode.getReason().getCode(),
                detailMessage,
                null
        );

        return super.handleExceptionInternal(
                ex,
                body,
                HttpHeaders.EMPTY,
                errorCode.getReasonHttpStatus().getHttpStatus(),
                request
        );
    }

    // 권한 부족 - 403 에러 응답 (security가 던진 예외를 여기서 처리)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException e, WebRequest request) {
        BaseErrorCode errorCode = ErrorStatus._FORBIDDEN;
        return handleExceptionInternalFalse(e, errorCode, HttpHeaders.EMPTY, errorCode.getReasonHttpStatus().getHttpStatus(), request, errorCode.getReason().getMessage());
    }

    // 처리되지 않은 예외 - 500 에러 응답
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        e.printStackTrace();
        BaseErrorCode errorCode = getErrorCodeByName("_INTERNAL_SERVER_ERROR");
        return handleExceptionInternalFalse(e, errorCode, HttpHeaders.EMPTY, errorCode.getReasonHttpStatus().getHttpStatus(), request, e.getMessage());
    }

    private BaseErrorCode getErrorCodeByName(String name) {
        try {
            return profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus.valueOf(name);
        } catch (IllegalArgumentException ex) {
            return profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus._INTERNAL_SERVER_ERROR;
        }
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(e, body, headers, reason.getHttpStatus(), webRequest);
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, BaseErrorCode errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getReason().getCode(), errorCommonStatus.getReason().getMessage(), errorPoint);
        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, BaseErrorCode errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getReason().getCode(), errorCommonStatus.getReason().getMessage(), errorArgs);
        return super.handleExceptionInternal(e, body, headers, errorCommonStatus.getReasonHttpStatus().getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, BaseErrorCode errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getReason().getCode(), errorCommonStatus.getReason().getMessage(), null);
        return super.handleExceptionInternal(e, body, headers, errorCommonStatus.getReasonHttpStatus().getHttpStatus(), request);
    }

}