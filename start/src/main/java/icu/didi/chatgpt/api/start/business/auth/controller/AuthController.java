package icu.didi.chatgpt.api.start.business.auth.controller;

import icu.didi.chatgpt.api.start.business.auth.req.LoginReq;
import icu.didi.chatgpt.api.start.business.auth.resp.LoginUserResp;
import icu.didi.chatgpt.api.start.business.auth.service.IAuthService;
import icu.didi.chatgpt.api.start.common.dto.LoginUserDTO;
import icu.didi.chatgpt.api.start.serialize.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author fujikang
 * @since 2023/10/19 11:11
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "登录控制器", description = "登录控制器")
public class AuthController {
    @Resource
    private IAuthService authService;

    @GetMapping("/verify")
    @Operation(summary = "校验token")
    public ResponseEntity<Void> verify(@RequestParam String token) {
        return StringUtils.equals("abc123", token) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/success")
    @Operation(summary = "校验token")
    public Response<String> success() {
        return Response.success("verify success");
    }

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Response<LoginUserResp> login(@RequestBody @Validated LoginReq loginReq) {
        return Response.success(authService.login(loginReq));
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息")
    public Response<LoginUserDTO> text() {
        return Response.success(null);
    }
}
