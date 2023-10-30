package icu.didi.chatgpt.api.start.serialize;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import static icu.didi.chatgpt.api.start.security.filter.JwtAuthenticationFilter.REQUEST_ID;

/**
 * @author fujikang
 * @since 2023/10/19 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Schema(description = "通用响应对象")
public class Response<T> {
    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "错误消息")
    private String message;

    @Schema(description = "请求id")
    private String requestId;

    @Schema(description = "成功请求响应数据体")
    private T data;

    public static <T> Response<T> success() {
        return Response.success(null);
    }

    public static <T> Response<T> success(T data) {
        return Response.<T>builder().success(true).code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).data(data).requestId(MDC.get(REQUEST_ID)).build();
    }

    public static <T> Response<T> fail() {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public static <T> Response<T> fail(final String msg) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static <T> Response<T> fail(final Integer code, final String msg) {
        return Response.<T>builder().success(false).code(code).message(msg).requestId(MDC.get(REQUEST_ID)).build();
    }

}
