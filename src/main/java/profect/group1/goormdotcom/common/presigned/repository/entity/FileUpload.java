package profect.group1.goormdotcom.common.presigned.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import profect.group1.goormdotcom.common.presigned.domain.FileDomain;
import profect.group1.goormdotcom.common.presigned.domain.FileStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "file_upload", indexes = {
        @Index(name = "idx_status_created_at", columnList = "status, created_at"),
        @Index(name = "idx_domain_status", columnList = "domain, status")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
public class FileUpload {
    @Id
    /*@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")*/
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 500)
    private String objectKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FileDomain domain;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FileStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

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
        LocalDateTime expirationTime = createdAt.plusHours(retentionHours);
        return LocalDateTime.now().isAfter(expirationTime);
    }
}
