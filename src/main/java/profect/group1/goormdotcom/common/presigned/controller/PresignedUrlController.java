package profect.group1.goormdotcom.common.presigned.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import profect.group1.goormdotcom.apiPayload.ApiResponse;
import profect.group1.goormdotcom.apiPayload.code.status.SuccessStatus;
import profect.group1.goormdotcom.common.presigned.controller.dto.ObjectKeyResponse;
import profect.group1.goormdotcom.common.presigned.controller.dto.PresignedUrlResponse;
import profect.group1.goormdotcom.common.presigned.controller.dto.UploadUrlRequest;
import profect.group1.goormdotcom.common.presigned.service.PresignedUrlService;

import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class PresignedUrlController {
    private final PresignedUrlService presignedUrlService;

    /**
     * 파일 업로드용 Presigned URL 발급
     */
    @PostMapping("/upload-url")
    public ResponseEntity<PresignedUrlResponse> generateUploadUrl(
            @Valid @RequestBody UploadUrlRequest request
    ) {
        PresignedUrlResponse response = presignedUrlService.generateUploadUrl(
                request.getFilename(),
                request.getDomain(),
                request.getContentType()
        );
        return ResponseEntity.ok(response);
        // return ApiResponse.of(SuccessStatus._OK, response);
    }

    /**
     * 파일 업로드 확정 (temp -> main)
     */
    @PostMapping("/{fileId}/confirm")
    public ResponseEntity<Void> confirmUpload(@PathVariable UUID fileId) {
        presignedUrlService.confirmUpload(fileId);
        return ResponseEntity.ok().build();
        // return ApiResponse.of(SuccessStatus._OK, null);
    }

    /**
     * 파일 URL 조회 (CloudFront)
     */
    @GetMapping("/{fileId}/url")
    public ResponseEntity<ObjectKeyResponse> getObjectKey(@PathVariable UUID fileId) {
        String url = presignedUrlService.getObjectKey(fileId);
        return ResponseEntity.ok(new ObjectKeyResponse(url));
        // return ApiResponse.of(SuccessStatus._OK, new ObjectKeyResponse(url));
    }
}


