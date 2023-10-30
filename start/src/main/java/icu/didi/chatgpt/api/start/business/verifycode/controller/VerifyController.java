package icu.didi.chatgpt.api.start.business.verifycode.controller;

import icu.didi.chatgpt.api.start.business.verifycode.service.IVerifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fujikang
 * @since 2023/10/27 14:53
 */
@RestController
@RequestMapping("/verify")
@Tag(name = "验证码", description = "验证码相关接口")
public class VerifyController {

    @Resource
    private IVerifyService verifyService;

    @Operation(summary = "创建验证码")
    @PostMapping("/create")
    public void create(String account) {
        verifyService.create(account);
    }


}
