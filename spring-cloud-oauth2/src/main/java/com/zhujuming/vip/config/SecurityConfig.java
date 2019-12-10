package com.zhujuming.vip.config;

import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsUtils;

/**
 * @Description: 安全配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //配置这个,以保证在刷新Token时能成功刷新
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //配置密码加密方式 BCryptPasswordEncoder,添加用户加密的时候也用这个加密
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected UserDetailsService userDetailsService() {
       //这里是添加两个用户到内存中去,实际中是从下面通过数据判断用户是否存在
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("zhujuming123");
        manager.createUser(User.withUsername("zhujuming").password(password).authorities("USER").build());
        manager.createUser(User.withUsername("seven").password(password).authorities("USER").build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http.requestMatchers().anyRequest().and().authorizeRequests().antMatchers("/oauth/**").permitAll();
    }

//    package com.foreign.payment.config;
//
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.web.cors.CorsUtils;
//
//    @Slf4j
//    @Configuration
//    @EnableWebSecurity
//    @EnableGlobalMethodSecurity(securedEnabled = true)
//    @Api("测试")
//    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        /**
//         * 需要放行的URL
//         */
//        private static final String[] AUTH_WHITELIST = {
////            "/**",
//                "/token",
//                // -- swagger ui
//                "/v2/api-docs",
//                "/swagger-resources",
//                "/swagger-resources/**",
//                "/configuration/ui",
//                "/configuration/security",
//                "/swagger-ui.html",
//                "/webjars/**"
//        };
//
//        // 设置 HTTP 验证规则
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.cors().and().csrf().disable()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().authorizeRequests()
//                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                    .antMatchers(AUTH_WHITELIST).permitAll();
//            //.anyRequest().authenticated()  // 所有请求需要身份认证
//            // .and().exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint())
//            // .and().addFilter(new AuthenticationFilter(authenticationManager())).exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);// 自定义访问失败处理器
//
//
//        }
//
//        //  @Autowired
//        // private CustomAccessDeniedHandler customAccessDeniedHandler;
//    }
}
