package com.henge.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JsonWebTokenAuthenticationProvider implements AuthenticationProvider {

    private JsonWebTokenUtility tokenService = new JsonWebTokenUtility();

    //spring security开始真正的进行身份认证操作
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticatedUser = null;
        //只处理PreAuthenticatedAuthenticationToken
        if (authentication.getClass()
                .isAssignableFrom(PreAuthenticatedAuthenticationToken.class)
                && authentication.getPrincipal() != null) {

            String tokenHeader = (String) authentication.getPrincipal();
            UserDetails userDetails = parseToken(tokenHeader);
            if (userDetails != null) {
                authenticatedUser = new JsonWebTokenAuthentication(userDetails, tokenHeader);
            }
        } else {
            //其他情况，直接赋值
            authenticatedUser = authentication;
        }
        return authenticatedUser;
    }

    //解析token信息
    private UserDetails parseToken(String tokenHeader) {
        UserDetails principal = null;
        AuthTokenDetails authTokenDetails = tokenService.parseAndValidate(tokenHeader);

        if (authTokenDetails != null) {
            List<GrantedAuthority> authorities = authTokenDetails.getRoleNames()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // userId介入Spring Security
            principal = new User(authTokenDetails.getId().toString(), "", authorities);
        }
        return principal;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(PreAuthenticatedAuthenticationToken.class) || authentication.isAssignableFrom(JsonWebTokenAuthentication.class);
    }

}
