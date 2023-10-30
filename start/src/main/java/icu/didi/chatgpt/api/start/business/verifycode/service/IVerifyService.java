package icu.didi.chatgpt.api.start.business.verifycode.service;

/**
 * @author fujikang
 * @since 2023/10/27 15:12
 */
public interface IVerifyService {
    void create(String account);
    Boolean verify(String uuid,String inputCode);
}
