package icu.didi.chatgpt.api.start.business.auth.service;

import icu.didi.chatgpt.api.start.business.auth.req.LoginReq;
import icu.didi.chatgpt.api.start.business.auth.resp.LoginUserResp;

/**
 * @author fujikang
 * @since 2023/10/26 17:31
 */
public interface IAuthService {
    LoginUserResp login(LoginReq loginReq);
}
