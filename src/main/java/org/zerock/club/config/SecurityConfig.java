package org.zerock.club.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.club.security.handler.ClubLoginSuccessHandler;
import org.zerock.club.security.service.ClubUserDetailsService;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // @PreAuthorize 사용 / 예전 버전의 @Secure 어노테이션 사용 가능
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private ClubUserDetailsService UserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1")
        .password("$2a$10$L8225E7Y.Hwut0j/DJ.6u.1FXWUgygDvUepZbiA5MBbbC28uVup9W")
        .roles("USER");
    } */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        // @EnableGlobalMethodSecurity 사용으로 주석처리
        /* http.authorizeRequests()
            .antMatchers("/sample/all").permitAll()
            .antMatchers("/sample/member").hasRole("USER"); // ROLE_USER 라는 상수와 같은 의미
         */

        http.formLogin();

        http.csrf().disable();

        http.logout();
        // CSRF를 사용하는 경우 바로 로그아웃 되지 않고 로그아웃 폼으로 이동됨(logout POST 방식 지원)
        // 비활성화 했기 때문에 GET 방식으로도 로그아웃이 되는 상태

        http.oauth2Login()
            .successHandler(successHandler());

        http.rememberMe()
            .tokenValiditySeconds(60 * 60 * 7)
            .userDetailsService(UserDetailsService);
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

}
