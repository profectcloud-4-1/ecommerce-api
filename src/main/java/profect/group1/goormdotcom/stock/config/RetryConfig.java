package profect.group1.goormdotcom.stock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stock.adjust.retry")
public record RetryConfig(
    Integer maxRetries,
    Long baseOffMs
) {
    public RetryConfig {
        if (maxRetries == null) maxRetries = 5;
        if (baseOffMs == null) baseOffMs = 20L;
    };
}