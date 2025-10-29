package profect.group1.goormdotcom.product.infrastructure.client.PresignedService;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import profect.group1.goormdotcom.common.config.FeignConfig;
import profect.group1.goormdotcom.product.infrastructure.client.PresignedService.dto.ObjectKeyResponse;

@FeignClient(
    name = "product-to-presigned",
    fallback = PresignedClientFallback.class,
    configuration = FeignConfig.class
)
public interface PresignedClient {
    
    @PostMapping("/api/files/{fileId}/confirm")
    public ResponseEntity<Void> confirmUpload(@PathVariable UUID fileId);

    @GetMapping("/api/files/{fileId}/url")
    public ResponseEntity<ObjectKeyResponse> getObjectKey(@PathVariable UUID fileId);
}