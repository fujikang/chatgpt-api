package icu.didi.chatgpt.api.start.exception;

import cn.hutool.jwt.JWTException;
import icu.didi.chatgpt.api.start.serialize.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fujikang
 * @since 2023/10/19 10:58
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(ChatgptException.class)
    @ResponseBody
    public Response<Void> chatgptException(HttpServletRequest req, HttpServletResponse response, ChatgptException exception) {
        log.error("【应用异常】 ".concat(exception.getMessage()), exception);
        return Response.fail(exception.getCode(), exception.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response<Void> runtimeException(HttpServletRequest req, HttpServletResponse response, RuntimeException exception) {
        log.error("【运行异常】 ".concat(exception.getMessage()), exception);
        return Response.fail(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<Void> exception(HttpServletRequest req, HttpServletResponse response, Exception exception) {
        log.error("【系统异常】 ".concat(exception.getMessage()), exception);
        return Response.fail(exception.getMessage());
    }
}
