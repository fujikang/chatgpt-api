package icu.didi.chatgpt.api.start.business.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.jwt.JWTUtil;
import icu.didi.chatgpt.api.start.business.auth.req.LoginReq;
import icu.didi.chatgpt.api.start.business.auth.resp.LoginUserResp;
import icu.didi.chatgpt.api.start.business.auth.service.IAuthService;
import icu.didi.chatgpt.api.start.business.verifycode.service.IVerifyService;
import icu.didi.chatgpt.api.start.exception.ChatgptException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author fujikang
 * @since 2023/10/26 17:31
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IVerifyService verifyService;


    @Override
    public LoginUserResp login(LoginReq loginReq) {
        Boolean verify = verifyService.verify(loginReq.getUsername(), loginReq.getVerifyCode());
        if (!verify){
            throw ChatgptException.exception("验证码错误");
        }
        LoginUserResp loginUserResp = new LoginUserResp();
        if (StringUtils.equals(loginReq.getUsername(), "admin") && StringUtils.equals(loginReq.getPassword(), "123456")) {
            loginUserResp.setBearerToken(JWTUtil.createToken(BeanUtil.beanToMap(loginReq.getUsername()),"1234456".getBytes()));
            return loginUserResp;
        }
        throw ChatgptException.exception("用户名或密码错误");
    }
}
