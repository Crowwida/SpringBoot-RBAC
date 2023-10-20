package com.henge.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.henge.model.RestResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class JsonWebTokenSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JsonWebTokenAuthenticationProvider authenticationProvider;

    @Autowired
    private JsonWebTokenAuthenticationFilter jsonWebTokenFilter;

    /**
     * 身份认证管理员进行处理
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 配置身份验证不通过的异常处理，以及身份验证的策略和时间
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Rest是无状态的，没有会话
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

                    //返回json形式的错误信息
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    RestResp restResp = RestResp.error(RestResp.NO_SESSION, "没有登录或登录已过期!");
                    response.getWriter().println(objectMapper.writeValueAsString(restResp));
                    response.getWriter().flush();
                }
        );

        setupAuthorization(http);
        http.addFilterBefore(jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //配置身份认证路由
    protected abstract void setupAuthorization(HttpSecurity http) throws Exception;
}