package icu.didi.chatgpt.api.start.security.configuration;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import icu.didi.chatgpt.api.start.common.dto.LoginUserDTO;
import icu.didi.chatgpt.api.start.exception.ChatgptException;
import icu.didi.chatgpt.api.start.properties.SpringSecurityProperties;
import icu.didi.chatgpt.api.start.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import static org.springframework.http.HttpMethod.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * @author fujikang
 * @since 2023/10/19 16:17
 * 配置流程
 * 1、禁用csrf 可选
 * 2、配置授权策略 哪些需要鉴权 哪些不需要鉴权
 * 3、配置session管理策略 jwt无状态
 * 4、配置认证器
 * 5、配置过滤器
 */
@SpringBootConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SpringSecurityProperties springSecurityProperties;
    private final UserDetailsService springSecurityUserDetailService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //禁用csrf(防止跨站请求伪造攻击)
                .csrf(AbstractHttpConfigurer::disable)
                //接口拦截策略
                .authorizeHttpRequests(
                        security ->
                                security
                                        // 设置白名单 如果这里配置了之后，会从上下文获取权限，与这里的比较
                                        .requestMatchers(springSecurityProperties.getIgnorePaths().toArray(new String[0])).permitAll()
                                        // 对于其他任何请求，都保护起来
                                        .anyRequest().authenticated()
                )
                // 禁用缓存
                .sessionManagement(
                        // 使用无状态session，即不使用session缓存数据
                        security -> security.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationManager(authenticationManager())
                // 添加JWT过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    /**
     * 配置跨源访问(CORS)
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(List.of(GET.name(), HEAD.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name(), OPTIONS.name(), TRACE.name()));
        corsConfiguration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * TODO 四 4.4 基于用户名和密码或使用用户名和密码进行身份验证
     * 认证管理器，登录的时候参数会传给 authenticationManager
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        AuthenticationProvider authenticationProvider = authenticationProvider();
        return new ProviderManager(authenticationProvider);
    }

    /**
     * @return 身份校验机制、身份验证提供程序
     * spring security 权限校验提供类
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 设置用户相信信息，可以从数据库中读取、或者缓存、或者配置文件
        authProvider.setUserDetailsService(springSecurityUserDetailService);
        // 设置加密机制，若想要尝试对用户进行身份验证，我们需要知道使用的是什么编码
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * 加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
