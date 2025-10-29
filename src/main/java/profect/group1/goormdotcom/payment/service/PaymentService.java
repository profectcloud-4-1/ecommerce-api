package profect.group1.goormdotcom.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.reactive.function.client.WebClient;
import profect.group1.goormdotcom.apiPayload.code.status.ErrorStatus;
import profect.group1.goormdotcom.apiPayload.exceptions.handler.PaymentHandler;
import profect.group1.goormdotcom.payment.config.TossPaymentConfig;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.request.*;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentCancelResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSearchResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSuccessResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.mapper.PaymentDtoMapper;
import profect.group1.goormdotcom.payment.domain.Payment;
import profect.group1.goormdotcom.payment.domain.PaymentHistory;
import profect.group1.goormdotcom.payment.domain.enums.Status;
import profect.group1.goormdotcom.payment.domain.enums.TossPaymentStatus;
import profect.group1.goormdotcom.payment.infrastructure.client.OrderClient;
import profect.group1.goormdotcom.payment.infrastructure.client.dto.PaymentResultDto;
import profect.group1.goormdotcom.payment.repository.PaymentHistoryRepository;
import profect.group1.goormdotcom.payment.repository.PaymentRepository;
import profect.group1.goormdotcom.payment.repository.entity.PaymentEntity;
import profect.group1.goormdotcom.payment.repository.mapper.PaymentHistoryMapper;
import profect.group1.goormdotcom.payment.repository.mapper.PaymentMapper;
import profect.group1.goormdotcom.user.domain.User;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final ApplicationEventPublisher eventPublisher;
    private final WebClient tossWebClient;
    private final OrderClient orderClient;

    @Transactional
    public Payment requestPayment(PaymentCreateRequestDto dto, User user) {
        //권한 확인
        /* if(!SecurityUtil.isCustomer()) {
            throw new PaymentHandler(ErrorStatus.INSUFFICIENT_ROLE);
        }*/

        //TODO: OrderClient를 통해서 order에서 orderId 존재하는지 확인받기, 주문 정보 조회 및 금액 검증

        //처리중인 결제내역이 있는지 확인
        if (dto.getOrderId() != null) {
            paymentRepository.findByOrderIdAndStatus(dto.getOrderId(), "PAY0000")
                    .ifPresent(existing -> {
                        throw new PaymentHandler(ErrorStatus._DUPLICATE_PAYMENT_REQUEST);
                    });
        }

        //TODO: orderNumber order에서 전달받아야 함. 추후 삭제
        String orderNumber = dto.getOrderNumber();
        if (orderNumber == null || orderNumber.isBlank()) {
            orderNumber = generateOrderNumber();
        }

        Payment payment = Payment.create(user.getId(), dto.getOrderId(), orderNumber, dto.getOrderName(), dto.getPayType(), dto.getAmount());

        //1000원 이하면 결제X
        if(payment.getAmount() < 1000) {
            throw new PaymentHandler(ErrorStatus._BAD_REQUEST);
        }

        PaymentEntity savedEntity = paymentRepository.save(PaymentMapper.toEntity(payment));
        payment.setId(savedEntity.getId());

        PaymentHistory history = PaymentHistory.create(
                savedEntity.getId(),
                "PAY0000",      //결제 대기
                payment.getAmount(),
                null,
                null,
                "REQUEST");

        paymentHistoryRepository.save(PaymentHistoryMapper.toEntity(history));

        return payment;
    }

    //TODO: Order에서 만들어서 넘어와야 함. 임시 구현, 추후 삭제
    private String generateOrderNumber() {
        var now = java.time.LocalDateTime.now();
        String ts = now.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String rand = java.util.UUID.randomUUID().toString().substring(0,5).toUpperCase();
        return "ORD-" + ts + "-" + rand;
    }

    @Transactional
    public PaymentSuccessResponseDto tossPaymentSuccess(PaymentSuccessRequestDto dto) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderNumber(dto.getOrderId())
                .orElseThrow(() -> new PaymentHandler(ErrorStatus._PAYMENT_NOT_FOUND));

        //멱등성 체크-> 이미 처리된 paymentKey인지 확인
        if(paymentRepository.existsByPaymentKey(dto.getPaymentKey())) {
            return null;
        }

        //히스토리 생성

        //금액 검증
        if (!paymentEntity.getAmount().equals(dto.getAmount())) {
            throw new PaymentHandler(ErrorStatus._INVALID_PAYMENT_AMOUNT);
        }

        String authorizations = "Basic " + Base64.getEncoder().encodeToString((tossPaymentConfig.getSecretKey() + ":").getBytes());

        //TODO: RestClient로 바꾸는 것 고려
        PaymentSuccessResponseDto response = tossWebClient.post()
                .uri("/confirm")
                .header("Authorization", authorizations)
                .header("Content-Type", "application/json")
                .bodyValue(java.util.Map.of(
                        "paymentKey", dto.getPaymentKey(),
                        "orderId", dto.getOrderId(),
                        "amount", dto.getAmount()
                ))
                .retrieve()
                .bodyToMono(PaymentSuccessResponseDto.class)
                .block();

        LocalDateTime approvedAt = null;
        if (response != null && response.approvedAt() != null) {
            approvedAt = response.approvedAt()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } else {
            approvedAt = LocalDateTime.now();
        }
        paymentEntity.setApprovedAt(approvedAt);
        paymentEntity.setPaymentKey(dto.getPaymentKey());
        paymentEntity.setStatus("PAY0001");
        paymentEntity.setApprovedAt(response.approvedAt().toLocalDateTime());

        //offsetTime 직렬화를 위한 클래스
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String rawResponseJson = null;
        try {
            rawResponseJson = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException ignored) {}

        PaymentHistory history = PaymentHistory.create(
                paymentEntity.getId(),
                "PAY0001",                 //결제 승인
                paymentEntity.getAmount(),
                paymentEntity.getPaymentKey(),
                rawResponseJson,
                "SUCCESS"
        );

        paymentRepository.save(paymentEntity);
        paymentHistoryRepository.save(PaymentHistoryMapper.toEntity(history));

        try {
            orderClient.notifyPaymentResult(
                    paymentEntity.getOrderId(),
                    new PaymentResultDto(
                            "PAY0001",                //결제 성공
                            paymentEntity.getAmount(),
                            response.approvedAt()
                    )
            );
        } catch (Exception e) {
            log.error("[ORDER SYNC] 주문서비스 결제결과 전달 실패: {}", e.getMessage());
        }
        return response;
    }

    @Transactional
    public void tossPaymentFail(PaymentFailRequestDto dto) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderNumber(dto.getOrderId())
                .orElseThrow(() -> new PaymentHandler(ErrorStatus._PAYMENT_NOT_FOUND));

        PaymentHistory history = PaymentHistory.create(
                paymentEntity.getId(),
                "PAY0002",                 //결제 실패
                paymentEntity.getAmount(),
                paymentEntity.getPaymentKey(),
                null,
                "FAILED"
        );

        paymentEntity.setStatus("PAY0002");

        paymentRepository.save(paymentEntity);
        paymentHistoryRepository.save(PaymentHistoryMapper.toEntity(history));
    }

    @Transactional
    public Payment tossPaymentCancel(PaymentCancelRequestDto dto) {
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(dto.getOrderId())
                .orElseThrow(() -> new PaymentHandler(ErrorStatus._PAYMENT_NOT_FOUND));

        //상태 검증
        if (paymentEntity.getStatus().equals("PAY0004"))
            throw new PaymentHandler(ErrorStatus._ALREADY_CANCELED_REQUEST);
        if (!paymentEntity.getStatus().equals("PAY0001")) {
            throw new PaymentHandler(ErrorStatus._INVALID_PAYMENT_STATUS);
        }

        //취소 요청 중 상태로 변경
        paymentEntity.setStatus("PAY0003");
        paymentRepository.save(paymentEntity);

        PaymentHistory history = PaymentHistory.create(
                paymentEntity.getId(),
                "PAY0003",                 //결제 취소 대기
                paymentEntity.getAmount(),
                paymentEntity.getPaymentKey(),
                dto.getReason(),
                "CANCEL_REQUESTED"
        );

        paymentHistoryRepository.save(PaymentHistoryMapper.toEntity(history));

        //트랜잭션 커밋 후 실행될 이벤트 -> PG에 결제 취소 요청
        eventPublisher.publishEvent(new PaymentCanceledEvent(paymentEntity.getId(), paymentEntity.getPaymentKey(), dto));

        return PaymentMapper.toDomain(paymentEntity);
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void executeCancel(PaymentCanceledEvent event) {
        String paymentKey = event.paymentKey();
        PaymentCancelRequestDto dto = event.requestDto();

        String authorizations = "Basic " + Base64.getEncoder().encodeToString((tossPaymentConfig.getSecretKey() + ":").getBytes());

        Map<String, Object> body = new HashMap<>();
        body.put("cancelReason", dto.getReason());

        try {
            //TODO: RestClient로 바꾸는 것 고려
            PaymentCancelResponseDto response = tossWebClient.post()
                    .uri("/{paymentKey}/cancel", paymentKey)
                    .header("Authorization", authorizations)
                    .header("Content-Type", "application/json")
                    //.header("Idempotency-Key", UUID.randomUUID().toString())
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(PaymentCancelResponseDto.class)
                    .block();

            //응답 검증
            if (response == null || response.cancels() == null || response.cancels().isEmpty()) {
                throw new PaymentHandler(ErrorStatus._PAYMENT_CANCEL_FAILED);
            }

            PaymentCancelResponseDto.CancelEntry lastCancel = response.cancels().get(response.cancels().size() - 1);

            //취소 상태 확인
            if (!"DONE".equalsIgnoreCase(lastCancel.cancelStatus())) {
                throw new PaymentHandler(ErrorStatus._PAYMENT_CANCEL_FAILED);
            }

            //DB 업데이트 (별도 트랜잭션 메서드 호출)
            updatePaymentCancelStatus(event.paymentId(), lastCancel.canceledAt().toLocalDateTime());

        } catch (Exception e) {
            //예외 발생 시 Toss 상태 재조회로 동기화 시도
            syncPaymentStatusFromToss(paymentKey);
        }
    }

    @Transactional
    public void updatePaymentCancelStatus(UUID paymentId, LocalDateTime canceledAt) {
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentHandler(ErrorStatus._PAYMENT_NOT_FOUND));

        paymentEntity.setStatus("PAY0004");
        paymentEntity.setCanceledAt(canceledAt);

        paymentRepository.saveAndFlush(paymentEntity);

        PaymentHistory history = PaymentHistory.create(
                paymentEntity.getId(),
                "PAY0004",                 //결제 취소 상태(전체)
                paymentEntity.getAmount(),
                paymentEntity.getPaymentKey(),
                null,
                "CANCEL_FULL_CONFIRMED"
        );

        paymentHistoryRepository.save(PaymentHistoryMapper.toEntity(history));
    }

    private void syncPaymentStatusFromToss(String paymentKey) {
        String authorization = "Basic " + Base64.getEncoder()
                .encodeToString((tossPaymentConfig.getSecretKey() + ":").getBytes());

        //offsetTime 직렬화를 위한 클래스
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            PaymentSuccessResponseDto tossResponse = tossWebClient.get()
                    .uri(tossPaymentConfig.getConfirmUrl() + paymentKey)
                    .header("Authorization", authorization)
                    .retrieve()
                    .bodyToMono(PaymentSuccessResponseDto.class)
                    .block();

            if (tossResponse != null) {
                PaymentEntity payment = paymentRepository.findByPaymentKey(paymentKey)
                        .orElseThrow(() -> new PaymentHandler(ErrorStatus._PAYMENT_NOT_FOUND));

                TossPaymentStatus tossStatus = TossPaymentStatus.valueOf(tossResponse.status().toUpperCase());
                String codePk = TossPaymentStatus.getCodeByTossStatus(tossStatus);
                String rawResponseJson = objectMapper.writeValueAsString(tossResponse);

                payment.setStatus(codePk);
                paymentRepository.save(payment);

                PaymentHistory history = PaymentHistory.create(
                        payment.getId(),
                        codePk,                 //결제 취소 상태(부분/전체)
                        payment.getAmount(),
                        paymentKey,
                        rawResponseJson,
                        "SYNCED_FROM_TOSS"
                );

                paymentHistoryRepository.save(PaymentHistoryMapper.toEntity(history));
            }
        } catch (Exception ex) {
            log.error("보상 트랜잭션 실패 (PG 상태 조회 실패): {}", ex.getMessage());
            //TODO: 별도처리
        }
    }

    private long calculateRefundableAmount(PaymentEntity payment) {
        long totalAmount = payment.getAmount();
        long canceledSoFar = payment.getCanceledAmount() != null ?
                payment.getCanceledAmount() : 0L;
        return totalAmount - canceledSoFar;
    }

    public PaymentSearchResponseDto search(UUID userId, PaymentSearchRequestDto requestDto, Pageable pageable) {
        Slice<PaymentEntity> slice = paymentRepository.findAllByUserId(
                userId,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.Direction.DESC,
                        "id"
                )
        );

        List<Payment> filtered = slice.getContent().stream()
                .map(PaymentMapper::toDomain)
                .filter(p -> requestDto.getStatus() == null || p.getStatus() == requestDto.getStatus())
                .filter(p -> {
                    LocalDateTime base = p.getCanceledAt() != null ? p.getCanceledAt()
                            : p.getApprovedAt() != null ? p.getApprovedAt()
                            : p.getCreatedAt();

                    return (requestDto.getFromAt() == null || !base.isBefore(requestDto.getFromAt()))
                            && (requestDto.getToAt() == null || !base.isAfter(requestDto.getToAt()));
                })
                .filter(p -> requestDto.getMinAmount() == null || p.getAmount() >= requestDto.getMinAmount())
                .filter(p -> requestDto.getMaxAmount() == null || p.getAmount() <= requestDto.getMaxAmount())
                .toList();

        List<PaymentSearchResponseDto.Item> items = PaymentDtoMapper.toSearchItemList(filtered);

        return new PaymentSearchResponseDto(
                items,
                new PaymentSearchResponseDto.Pagination(
                        slice.getNumber(),
                        slice.getSize(),
                        slice.hasNext(),
                        !slice.hasNext()
                )
        );
    }
}


