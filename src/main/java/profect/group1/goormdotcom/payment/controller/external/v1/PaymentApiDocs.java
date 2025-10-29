package profect.group1.goormdotcom.payment.controller.external.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.request.PaymentSearchRequestDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentCancelResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.request.PaymentCreateRequestDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.request.PaymentFailRequestDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.request.PaymentSuccessRequestDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSearchResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSuccessResponseDto;
import profect.group1.goormdotcom.user.domain.User;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Tag(name = "결제 관리", description = "결제 관련 API")
public interface PaymentApiDocs {

    @Operation(
            summary = "결제 성공 리디렉션 API",
            description = "결제 성공 시 리디렉션되는 API입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공입니다"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class))
            )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<PaymentSuccessResponseDto> tossPaymentSuccess(
            @ModelAttribute @Valid PaymentSuccessRequestDto paymentSuccessRequestDto
    );

    @Operation(
            summary = "결제 실패 리디렉션 API",
            description = "결제 실패 시 리디렉션되는 API입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공입니다"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class))
            )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<Void> tossPaymentFail(
            @ModelAttribute @Valid PaymentFailRequestDto paymentFailRequestDto
    );

    @Operation(
            summary = "결제 취소 API",
            description = "승인된 결제를 취소합니다. 토스에서 발급된 paymentKey(쿼리 파라미터)와 취소 사유/금액 등은 ModelAttribute DTO로 전달됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공입니다"),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class))
            )
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<PaymentCancelResponseDto> tossPaymentCancel(
            @ModelAttribute @Valid profect.group1.goormdotcom.payment.controller.external.v1.dto.request.PaymentCancelRequestDto paymentCancelRequestDto
    );

    @Operation(
            summary = "결제 내역 조회 API (Slice)",
            description = """
            사용자별 결제 내역을 Slice(무한 스크롤) 형태로 조회합니다.<br><br>
            • 필터 조건: 결제 상태(status), 결제 시각(fromAt~toAt), 금액 범위(minAmount~maxAmount)<br>
            • 페이지네이션은 Slice 기반으로, 전체 건수(totalCount)는 반환하지 않습니다.<br>
            • 기본 정렬: 결제 생성일(createdAt) 내림차순입니다.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공입니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = profect.group1.goormdotcom.apiPayload.ApiResponse.class)))
    })
    profect.group1.goormdotcom.apiPayload.ApiResponse<
            profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSearchResponseDto> searchPayment(
            @ModelAttribute PaymentSearchRequestDto paymentSearchRequestDto,
            Pageable pageable
    );
}
