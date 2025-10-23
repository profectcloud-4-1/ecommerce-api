package profect.group1.goormdotcom.order.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/** 데모용: 메모리로 배송 상태를 보관 (PREPARING / SHIPPED / DELIVERED / RETURNED) */
@Service
public class ShippingServiceImpl implements ShippingService {

    private final ConcurrentHashMap<UUID, String> status = new ConcurrentHashMap<>();

    @Override
    public void markShipped(UUID orderId) {
        status.put(orderId, "SHIPPED");
    }

    @Override
    public void markDelivered(UUID orderId) {
        status.put(orderId, "DELIVERED");
    }

    @Override
    public void markReturned(UUID orderId) {
        status.put(orderId, "RETURNED");
    }

    @Override
    public String getStatus(UUID orderId) {
        return status.getOrDefault(orderId, "PREPARING");
    }
}
