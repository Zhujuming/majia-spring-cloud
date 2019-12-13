package com.zhujuming.vip.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsUtils;

/**
 * @Description: 安全配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 请配置这个，以保证在刷新Token时能成功刷新
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        // 配置密码加密方式 BCryptPasswordEncoder，添加用户加密的时候请也用这个加密
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");//基于内存管理的角色授权登录系统

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }


//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        // 这里是添加两个用户到内存中去，实际中是从#下面去通过数据库判断用户是否存在
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
//        MyPasswordEncoder passwordEncode = new MyPasswordEncoder();
////        String pwd = passwordEncode.encode("123456");
//        String pwd = passwordEncoder().encode("123456");
//        manager.createUser(User.withUsername("user_1").password(pwd).authorities("USER").build());
//        return manager;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.requestMatchers().anyRequest().and().authorizeRequests().antMatchers("/oauth/**").permitAll();
        // @formatter:on
    }



//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {



//
//    //需要放行的URL
//    private static final String[] AUTH_WHITELIST = {
//            "/token",
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
//            "/oauth/**"
//    };
//
//    //配置这个,以保证在刷新Token时能成功刷新
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        //配置密码加密方式 BCryptPasswordEncoder,添加用户加密的时候也用这个加密
//        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    protected UserDetailsService userDetailsService() {
//        //这里是添加两个用户到内存中去,实际中是从下面通过数据判断用户是否存在
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("zhujuming123");
//        manager.createUser(User.withUsername("zhujuming").password(password).authorities("USER").build());
//        manager.createUser(User.withUsername("seven").password(password).authorities("USER").build());
//        return manager;
//    }
//
//    //设置HTTP验证规则
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .anyRequest().authenticated(); // 所有请求需要身份认证
//    }

}
