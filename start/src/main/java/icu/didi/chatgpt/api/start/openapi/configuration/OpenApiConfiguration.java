package icu.didi.chatgpt.api.start.openapi.configuration;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fujikang
 * @since 2023/10/26 18:08
 */
@Configuration
public class OpenApiConfiguration {


    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chatgpt-api")
                        .version("1.0")
                        .description("基于JDK17+SpringBoot3+Mybatis-plus3开发Chatgpt-api项目")
                        .termsOfService("https://chatgpt-api.didi.icu/doc.html")
                        .license(
                                new License().name("Apache 2.0")
                                .url("https://chatgpt-api.didi.icu/doc.html")
                        )
                );
    }

}
