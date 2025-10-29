package profect.group1.goormdotcom.product.infrastructure.client.PresignedService;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import profect.group1.goormdotcom.product.infrastructure.client.PresignedService.dto.ObjectKeyResponse;

public class PresignedClientFallback {
    
    @PostMapping("/api/files/{fileId}/confirm")
    public ResponseEntity<Void> confirmUpload(@PathVariable UUID fileId) {
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/api/files/{fileId}/url")
    public ResponseEntity<ObjectKeyResponse> getObjectKey(@PathVariable UUID fileId) {
        return ResponseEntity.internalServerError().build();
    }
}
