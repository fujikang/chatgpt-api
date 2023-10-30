package icu.didi.chatgpt.api.start.business.verifycode.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import icu.didi.chatgpt.api.start.business.verifycode.service.IVerifyService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static icu.didi.chatgpt.api.start.security.filter.JwtAuthenticationFilter.REQUEST_ID;

/**
 * @author fujikang
 * @since 2023/10/27 15:12
 */
@Service
@Slf4j
public class VerifyServiceImpl implements IVerifyService {
    @Resource
    private HttpServletResponse httpServletResponse;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    @SneakyThrows
    public void create(String account) {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(200, 100, 4);
        String code = gifCaptcha.getCode();
        redisTemplate.opsForValue().set(account, code);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("image/gif");
        httpServletResponse.setHeader("Pragma", "No-cache");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0L);
        httpServletResponse.getOutputStream().write(gifCaptcha.getImageBytes());
    }


    @Override
    public Boolean verify(String uuid, String inputCode) {
        Object valueObj = redisTemplate.opsForValue().getAndDelete(uuid);
        if (Objects.isNull(valueObj)) {
            return false;
        }
        return StringUtils.equals(inputCode, String.valueOf(valueObj));
    }
}
