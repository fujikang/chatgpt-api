package icu.didi.chatgpt.api.start.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import icu.didi.chatgpt.api.start.common.util.ServletUtils;
import icu.didi.chatgpt.api.start.properties.JwtProperties;
import icu.didi.chatgpt.api.start.properties.SpringSecurityProperties;
import icu.didi.chatgpt.api.start.serialize.Response;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @author fujikang
 * @since 2023/10/19 10:26
 * <p>
 * SS三个核心
 * authentication-存储了认证信息，代表当前登录用户
 * Principal：用户信息，没有认证时一般是用户名，认证后一般是用户对象
 * Credentials：用户凭证，一般是密码
 * Authorities：用户权限
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER = "Bearer ";
    public static final String REQUEST_ID = "REQUEST-ID";
    private final JwtProperties jwtProperties;
    private final SpringSecurityProperties springSecurityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put("REQUEST-ID", UUID.randomUUID().toString(true));
            String servletPath = request.getServletPath();
            List<String> ignorePaths = springSecurityProperties.getIgnorePaths();
            if (CollectionUtil.isNotEmpty(ignorePaths)) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                if (ignorePaths.stream().anyMatch(path -> antPathMatcher.match(path, servletPath))) {
                    //如果当前地址包含放行地址，直接放行
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            final String bearerToken = request.getHeader(Header.AUTHORIZATION.getValue());
            if (StrUtil.isBlank(bearerToken)) {
                logger.error("token为空");
                ServletUtils.write2http(HttpStatus.UNAUTHORIZED, response, Response.fail("未登录"));
                return;
            }
            String token = removeBearerTokenPrefix(bearerToken);
            byte[] keyBytes = jwtProperties.getKeyBytes();
            boolean verify = JWTUtil.verify(token, keyBytes);
            if (!verify) {
                logger.error("token验证失败");
                ServletUtils.write2http(HttpStatus.UNAUTHORIZED, response, Response.fail("未登录"));
                return;
            }
            JWT jwt = JWT.of(token).setKey(keyBytes);
            JSONObject payloads = jwt.getPayloads();
            String str = payloads.getStr("username");


        } catch (JWTException jwtException) {
            logger.error("token解析异常");
            ServletUtils.write2http(HttpStatus.UNAUTHORIZED, response, Response.fail("登录失败"));
        } catch (Exception exception) {
            logger.error("未知异常", exception);
            ServletUtils.write2http(HttpStatus.UNAUTHORIZED, response, Response.fail("登录系统异常"));
        } finally {
            MDC.clear();
        }

    }

    public String removeBearerTokenPrefix(String bearerToken) {
        return bearerToken.replace(BEARER, "");
    }
}
