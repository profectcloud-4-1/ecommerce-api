package profect.group1.goormdotcom.category.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record CategoryNode(
    UUID id,
    UUID parentId,
    String name,
    List<CategoryNode> children
) {
    public static CategoryNode of(UUID id, UUID parentId, String name) {
        return new CategoryNode(id, parentId, name, new ArrayList<>());
    }
}

