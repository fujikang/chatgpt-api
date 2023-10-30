package icu.didi.chatgpt.api.start.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author fujikang
 * @since 2023/10/19 11:35
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "spring.security")
public class SpringSecurityProperties {
    /**
     * 地址忽略
     */
    private List<String> ignorePaths;


    public List<String> getIgnorePaths() {
        return CollectionUtils.isEmpty(ignorePaths) ? Collections.emptyList() : ignorePaths;
    }
}
