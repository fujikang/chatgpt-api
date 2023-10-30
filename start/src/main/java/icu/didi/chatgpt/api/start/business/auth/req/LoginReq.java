package icu.didi.chatgpt.api.start.business.auth.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fujikang
 * @since 2023/10/19 11:12
 */
@Data
@Schema(description = "登录请求")
public class LoginReq implements Serializable {

    @NotBlank
    @Schema(description = "用户名")
    private String username;

    @NotBlank
    @Schema(description = "密码")
    private String password;

    @NotBlank
    @Schema(description = "验证码")
    private String verifyCode;
}
