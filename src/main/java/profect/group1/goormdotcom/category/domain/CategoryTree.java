package profect.group1.goormdotcom.category.domain;

import java.util.Map;
import java.util.UUID;

public record CategoryTree(
    CategoryNode root,
    Map<UUID, CategoryNode> tree
) { }