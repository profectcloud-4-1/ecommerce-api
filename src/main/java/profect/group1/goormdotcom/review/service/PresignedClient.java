package profect.group1.goormdotcom.review.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;
@FeignClient(
        name = "review-to-presigned"
)
public interface PresignedClient {

    @PostMapping("/api/files/{fileId}/confirm")
    ResponseEntity<Void> confirmUpload(@PathVariable("fileId") UUID fileId);

    @GetMapping("/api/files/{fileId}/url")
    public ResponseEntity<ObjectKeyResponseDto> getObjectKey(@PathVariable UUID fileId);
}