package com.hh.ged.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by rvl on 07/11/2017.
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService myUserDetailsService;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    // The TokenStore bean provided at the AuthorizationConfig
    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("accessTokenConverter")
    JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private TokenEnhancer tokenEnhancer;


    public DefaultTokenServices tokenServices() {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));

        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(enhancerChain);
        defaultTokenServices.setAuthenticationManager(authenticationManager);

        return defaultTokenServices;
    }

    // To allow the rResourceServerConfigurerAdapter to understand the token,
    // it must share the same characteristics with AuthorizationServerConfigurerAdapter.
    // So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {

        resources
            .resourceId(resourceId)
            .tokenServices(tokenServices())
            .tokenStore(tokenStore);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .requestMatcher(new OAuthRequestedMatcher())
            .authorizeRequests()
                        .antMatchers(
                HttpMethod.GET,
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/**/*.ttf",
                "/swagger-resources/**",
                "/v2/api-docs/**"
            ).anonymous()
            .anyRequest().authenticated();
    }

    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            // Determine if the resource called is "/dolrest/**"
//            String path = request.getServletPath();
//            if ( path.length() >= 9 ) {
//                path = path.substring(0, 9);
//                boolean isApi = path.equals("/dolrest/");
//                return isApi;
//            } else return false;

            return true;
        }
    }

}
