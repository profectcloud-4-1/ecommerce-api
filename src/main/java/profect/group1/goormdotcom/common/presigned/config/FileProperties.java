package profect.group1.goormdotcom.common.presigned.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileProperties {
    private int presignedUrlExpiration = 3600;
    private int tempRetentionHours = 48;
}
