package com.hh.ged.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by rvl on 04/07/2017.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity
//@PropertySource("file:/opt/dol/config/jwt.properties")
//Permet de mettre cette config apres le RecourceConfig du oauth2
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService myUserDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(this.myUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity
            .formLogin().disable()
            .anonymous().disable()
            .httpBasic().disable();
        // @formatter:on
    }

}


//        httpSecurity
//            // we don't need CSRF because our token is invulnerable
//            .csrf().disable()
//
//            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//
//            // don't create session
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
//            .authorizeRequests()
//
//            .antMatchers(
//                HttpMethod.GET,
//                "/",
//                "/*.html",
//                "/favicon.ico",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js",
//                "/**/*.png",
//                "/**/*.ttf",
//                "/swagger-resources/**",
//                "/v2/api-docs/**"
//            ).anonymous()
//            .antMatchers("/auth/**").permitAll()
//            .anyRequest().authenticated();
//
//        // Custom JWT based security filter
//        httpSecurity
//            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//
//        // disable page caching
//        httpSecurity.headers().cacheControl();
