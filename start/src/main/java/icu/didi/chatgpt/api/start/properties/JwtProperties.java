package icu.didi.chatgpt.api.start.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;

/**
 * @author fujikang
 * @since 2023/10/19 11:36
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String key;


    public byte[] getKeyBytes() {
        return key.getBytes(StandardCharsets.UTF_8);
    }


}
