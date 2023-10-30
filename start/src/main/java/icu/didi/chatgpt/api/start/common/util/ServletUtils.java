package icu.didi.chatgpt.api.start.common.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

/**
 * @author fujikang
 * @since 2023/10/26 16:44
 */
public class ServletUtils {
    @SneakyThrows
    public static <T> void write2http(HttpStatus status, HttpServletResponse response, T data) {
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.getValue());
        response.setStatus(status.value());
        response.getWriter().write(JSONUtil.toJsonStr(data));
    }


}
