package com.henge.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * JTW验证
 */
@Component
public class JsonWebTokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    /**
     * 访问请求时，需要添加token请求头
     * GET http://localhost:10000/resource/0
     * Authorization：eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwIiwidXNlcm5hbWUiOiJhZG1pbiIsInJvbGVOYW1lcyI6WyLotoXnrqEiLCLotKLliqEiXSwiZXhwIjoxNTc2NjU1NTAzfQ.28BSMgNdxcGkOA_SB-E9KqkTZOoKIyy0ie2nVzJYZMIUQ2z018TlIc5OaKzDuRKr9GD_jyya2O7UGdXnLw9mpg
     *
     */
    public JsonWebTokenAuthenticationFilter() {
        this.setExceptionIfHeaderMissing(false);
        this.setPrincipalRequestHeader("Authorization");    //检查是否有Authorization请求头
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}