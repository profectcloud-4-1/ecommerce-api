package profect.group1.goormdotcom.common.presigned.repository.entity;


import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;

import profect.group1.goormdotcom.common.domain.BaseEntity;
import profect.group1.goormdotcom.common.presigned.domain.FileDomain;
import profect.group1.goormdotcom.common.presigned.domain.FileStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_file_upload", indexes = {
        @Index(name = "idx_status_created_at", columnList = "status, created_at"),
        @Index(name = "idx_domain_status", columnList = "domain, status")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class FileUpload extends BaseEntity {
    @Id
    @UuidGenerator

    private UUID id;

    @Column(nullable = false, length = 500)
    private String objectKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FileDomain domain;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FileStatus status;

    @Column
    private LocalDateTime deletedAt;

    public void confirm(String newObjectKey) {
        this.objectKey = newObjectKey;
        this.status = FileStatus.CONFIRMED;
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isTemp() {
        return this.status == FileStatus.TEMP;
    }

    public boolean isExpired(int retentionHours) {
        if (!isTemp()) {
            return false;
        }
        LocalDateTime expirationTime = this.getCreatedAt().plusHours(retentionHours);
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
