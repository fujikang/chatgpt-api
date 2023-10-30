package icu.didi.chatgpt.api.start.business.auth.resp;

import icu.didi.chatgpt.api.start.common.dto.LoginUserDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fujikang
 * @since 2023/10/19 11:15
 */
@Data
public class LoginUserResp implements Serializable {

    private LoginUserDTO loginUser;

    private String bearerToken;
}
