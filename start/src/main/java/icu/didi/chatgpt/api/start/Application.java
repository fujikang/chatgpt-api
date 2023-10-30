package icu.didi.chatgpt.api.start;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;

/**
 * @author fujikang
 * @since 2023/10/19 09:57
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Application.class, args);
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}{}\n\t" +
                        "External: \thttp://{}:{}{}\n\t" +
                        "Doc: \thttp://{}:{}{}/doc.html\n" +
                        "----------------------------------------------------------",
                environment.getProperty("spring.application.name"),
                environment.getProperty("server.port"),
                environment.getProperty("server.servlet.context-path"),
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"),
                environment.getProperty("server.servlet.context-path"),
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port"),
                environment.getProperty("server.servlet.context-path")
        );

    }
}
