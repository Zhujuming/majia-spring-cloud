package com.zhujuming.vip.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.NoOpTypeParameterMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 认证模块配置(基于Redis存储JWT)
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    // 资源ID
    private static final String SOURCE_ID = "majiaxueyuanApp";
    private static final int ACCESS_TOKEN_TIMER = 60 * 60 * 24;
    private static final int REFRESH_TOKEN_TIMER = 60 * 60 * 24 * 30;

    // 665326190 码家学院官方粉丝群

    @Autowired
    AuthenticationManager authenticationManager;
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Oauth授权登陆的时候 grant_type=authorization_code
        clients.inMemory().withClient("myapp").resourceIds(SOURCE_ID).authorizedGrantTypes("password", "refresh_token")
                .scopes("all").authorities("ADMIN").secret("lxapp").accessTokenValiditySeconds(ACCESS_TOKEN_TIMER)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_TIMER);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(accessTokenConverter());
//        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
//        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // 允许表单认证
        oauthServer.tokenKeyAccess("permitAll()");
        oauthServer.checkTokenAccess("isAuthenticated()");
        oauthServer.allowFormAuthenticationForClients();
        oauthServer.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // JWT增强
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token总需要封装的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String name = authentication.getUserAuthentication().getName();
                //得到用户名,去处理数据库可以拿到当前用户的信息和角色信息(需要传递到服务中用到的信息)
                HashMap<String, Object> additionalInformationmap = new HashMap<>();
                //map假装用户实体
                HashMap<Object, Object> userInfo = new HashMap<>();
                userInfo.put("id","1");
//                userInfo.put("username","zhujuming");
                userInfo.put("username","LiaoXiang");
                userInfo.put("qqnum","438944209");
                userInfo.put("userFlag","1");
                additionalInformationmap.put("userInfo", JSON.toJSONString(userInfo));
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformationmap);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken,authentication);
                return enhancedToken;
            }
        };
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey("majiaxueyuanSigningKey");
        return accessTokenConverter;
    }

//    @Bean
//    public TokenStore tokenStore() {
//        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//        return tokenStore;
//    }



//    //资源ID
//    private static final String SOURCE_ID="zhujumingApp";
//    private static final int ACCESS_TOKEN_TIMER=60 *60 *24;
//    private static final int REFRESH_TOKEN_TIMER=60 *60 *24 *30;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//    //redis连接工具
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory().withClient("myapp").resourceIds(SOURCE_ID)
//                .authorizedGrantTypes("password","refresh_token").scopes("all")
//                .authorities("ADMIN").secret("sevenapp").accessTokenValiditySeconds(ACCESS_TOKEN_TIMER)
//                .refreshTokenValiditySeconds(REFRESH_TOKEN_TIMER);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.accessTokenConverter(accessTokenConverter());
//        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        //允许表单认证
//        security.allowFormAuthenticationForClients();
//    }
//
//    //JWT增强
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter(){
//
//        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter(){
//
//            //重写增强token方法
//            @Override
//            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//                String name = authentication.getUserAuthentication().getName();
//                //得到用户名,去处理数据库可以拿到当前用户的信息和角色信息(需要传递到服务中用到的信息)
//                HashMap<String, Object> additionalInformationmap = new HashMap<>();
//                //map假装用户实体
//                HashMap<Object, Object> userInfo = new HashMap<>();
//                userInfo.put("id","1");
//                userInfo.put("username","zhujuming");
//                userInfo.put("QQ","893371542");
//                userInfo.put("userFlag","1");
//                additionalInformationmap.put("userInfo", JSON.toJSONString(userInfo));
//                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformationmap);
//                OAuth2AccessToken enhancedToken = super.enhance(accessToken,authentication);
//                return enhancedToken;
//            }
//        };
//        //测试用,资源服务使用相同的字符达到一个对称加密的效果,生产的时候使用RSA非对称加密方式
//        accessTokenConverter.setSigningKey("zhujumingSigningKey");
//        return accessTokenConverter;
//    }
//
//    @Bean
//    public TokenStore tokenStore(){
//        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//        return tokenStore();
//    }
}
