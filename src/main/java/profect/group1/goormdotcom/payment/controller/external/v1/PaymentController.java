package profect.group1.goormdotcom.payment.controller.external.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.request.*;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentCancelResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSearchResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSuccessResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.mapper.PaymentDtoMapper;
import profect.group1.goormdotcom.payment.domain.Payment;
import profect.group1.goormdotcom.payment.domain.enums.Status;
import profect.group1.goormdotcom.payment.service.PaymentService;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController implements PaymentApiDocs {
    private final PaymentService paymentService;

    @Override
    @GetMapping("/toss/success")
    public ApiResponse<PaymentSuccessResponseDto> tossPaymentSuccess(@ModelAttribute @Valid PaymentSuccessRequestDto paymentSuccessRequestDto) {
        return ApiResponse.onSuccess(paymentService.tossPaymentSuccess(paymentSuccessRequestDto));
    }

    @Override
    @GetMapping("/toss/fail")
    public ApiResponse<Void> tossPaymentFail(@ModelAttribute @Valid PaymentFailRequestDto paymentFailRequestDto) {
        paymentService.tossPaymentFail(paymentFailRequestDto);
        return ApiResponse.onSuccess(null);
    }

    @Override
    @PostMapping("/toss/cancel")
    public ApiResponse<PaymentCancelResponseDto> tossPaymentCancel(@ModelAttribute @Valid PaymentCancelRequestDto paymentCancelRequestDto) {
        Payment payment = paymentService.tossPaymentCancel(paymentCancelRequestDto);
        return ApiResponse.onSuccess(new PaymentCancelResponseDto(payment.getPaymentKey(), "PAY0003", List.of()));
    }

    @Override
    @GetMapping
    //TODO: @AuthenticationPrincipal User user 추가
    public ApiResponse<PaymentSearchResponseDto> searchPayment (@ModelAttribute PaymentSearchRequestDto paymentSearchRequestDto,
                                                                Pageable pageable) {
        //임시
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        return ApiResponse.onSuccess(paymentService.search(userId, paymentSearchRequestDto, pageable));
    }

}
