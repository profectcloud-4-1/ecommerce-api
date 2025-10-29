package profect.group1.goormdotcom.payment.controller.external.v1.mapper;

import org.springframework.stereotype.Component;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSearchResponseDto;
import profect.group1.goormdotcom.payment.controller.external.v1.dto.response.PaymentSuccessResponseDto;
import profect.group1.goormdotcom.payment.domain.Payment;
import profect.group1.goormdotcom.payment.repository.entity.PaymentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentDtoMapper {
    public static PaymentResponseDto toPaymentDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getPayType(),
                payment.getStatus(),
                payment.getAmount()
        );
    }

    public static List<PaymentSearchResponseDto.Item> toSearchItemList(List<Payment> payments) {
        if (payments == null) {
            return null;
        }

        return payments.stream()
                .map(PaymentDtoMapper::toSearchItem)
                .collect(Collectors.toList());
    }

    public static PaymentSearchResponseDto.Item toSearchItem(Payment payment) {
        LocalDateTime approved = payment.getApprovedAt();
        LocalDateTime canceled = payment.getCanceledAt();
        LocalDateTime statusAt =
                canceled != null ? canceled : approved;

        return new PaymentSearchResponseDto.Item(
                payment.getId(),
                payment.getOrderNumber(),
                payment.getOrderName(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPayType(),
                statusAt,
                approved,
                canceled
        );
    }
}