package icu.didi.chatgpt.api.start.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author fujikang
 * @since 2023/10/26 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO implements UserDetails, Serializable {
    /**
     * Description: 该用户所拥有的权限，如果细分为角色和权限，可以把两个都放到这个集合里面，
     * 比如ROLE_ADMIN,user:create可以同时存入
     */
    @Schema(description = "权限集合")
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    @JsonIgnore
    public String getPassword() {
        return null;
    }

    @Override
    @Schema(description = "用户名")
    public String getUsername() {
        return null;
    }

    @Override
    @Schema(description = "账号是否过期")
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @Schema(description = "账号是否被锁")
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @Schema(description = "凭证是否过期")
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @Schema(description = "账号是否启用")
    public boolean isEnabled() {
        return false;
    }
}
