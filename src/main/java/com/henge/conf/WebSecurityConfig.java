package com.henge.conf;

import com.henge.jwt.JsonWebTokenSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends JsonWebTokenSecurityConfig {
    @Override
    protected void setupAuthorization(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //允许匿名用户访问/user/login节点,不需要验证，但是也需要后台有页面才行，否则报错404
                .antMatchers("/user/login").permitAll()
                /**
                 * POST http://localhost:10000/user/login
                 * body: {"username":"admin","password":111111}
                 */
                .antMatchers("/swagger/**").permitAll()     //可用于后期展示api,目前没有页面
                .antMatchers("/web/**").permitAll()     //对应有后台的static.web资源目录
                .antMatchers("/").permitAll()

                //验证所有其他请求
                .anyRequest().authenticated();
    }
}
