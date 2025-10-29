package profect.group1.goormdotcom.order.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import profect.group1.goormdotcom.common.domain.BaseEntity;
import profect.group1.goormdotcom.order.repository.entity.OrderProductEntity;

public final class OrderNameFormatter {
    private OrderNameFormatter(){}
    //규칙: 대표상품명 + 외 N종
    public static String makeOrderName(List<OrderProductEntity> items){
        if (items == null || items.isEmpty()) return null;

        //대표 라인 선정: 금액desc -> 수량desc -> 생성시각 asc
        OrderProductEntity head = items.stream()
            .sorted(
                Comparator.comparingInt(OrderProductEntity::getTotalAmount).reversed()
                .thenComparingInt(OrderProductEntity::getQuantity).reversed()
                // .thenComparing(BaseEntity::getCreatedAt)
            )
            .findFirst()
            .orElse(items.get(0));
        String headName = head.getProductName();

        //종 계산 product(id) 기준으로 distinct 개수 -1
        long distinctKinds = items.stream()
            .map(OrderProductEntity::getProductId)   // ProductEntity의 PK
            .collect(Collectors.toSet())
            .size();

            long others = Math.max(0, distinctKinds - 1);
            if (others <= 0) return headName;
            return headName + "외" + others + "종";
    }
}
