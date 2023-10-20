package com.henge.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUtil {

    //spring security获取当前登录用户信息
    public static Long getCurrUid() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (userDetails != null) {
            return Long.valueOf(userDetails.getUsername());
        }
        return null;
    }

    //清空页面上下文
    public static void clear() {
        SecurityContextHolder.clearContext();
    }
}
