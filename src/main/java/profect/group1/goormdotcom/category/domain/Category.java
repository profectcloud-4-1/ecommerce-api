package profect.group1.goormdotcom.category.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    private UUID id;
    private UUID parentId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Category(
        UUID id,
        UUID parentId,
        String name
    ) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
