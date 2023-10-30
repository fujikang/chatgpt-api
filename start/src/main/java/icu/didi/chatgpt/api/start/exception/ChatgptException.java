package icu.didi.chatgpt.api.start.exception;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;


/**
 * @author fujikang
 * @since 2023/10/19 10:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Accessors(chain = true)
public class ChatgptException extends RuntimeException {

    private Integer code;

    private String message;

    public ChatgptException() {
    }

    public ChatgptException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }

    public static ChatgptException exception(String message) {
        return new ChatgptException(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public ChatgptException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public static ChatgptException exception(Integer code, String message) {
        return new ChatgptException(code, message);
    }

    public ChatgptException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }

    public static ChatgptException exception(String message, Throwable throwable) {
        return new ChatgptException(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, throwable);
    }

    public ChatgptException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }

    public static ChatgptException exception(Integer code, String message, Throwable throwable) {
        return new ChatgptException(code, message, throwable);
    }
}
