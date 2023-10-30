package icu.didi.chatgpt.api.start.security;

import icu.didi.chatgpt.api.start.common.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author fujikang
 * @since 2023/10/27 11:45
 */
@Component
@Slf4j
public class SpringSecurityUserDetailServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    /**
     * Description: 获取当前用户的方法，使用框架的上下文获取当前请求的用户
     */
    public static Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("进入查找用户的方法=====使用Mybatis-plus的Model模式；无需Server和Mapper，直接让实体类具备CURD的操作");
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        return loginUserDTO;
    }

    /**
     * Description: UserDetails是springsecurity框架的授权用户实体
     * 如果用户更改密码了会重新刷新token或者退出登录，
     *
     * @author: GuoTong
     * @date: 2023-06-16 21:44:31
     * @return:
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return StringUtils.equals(user.getPassword(), newPassword)? user : null;
    }
}
