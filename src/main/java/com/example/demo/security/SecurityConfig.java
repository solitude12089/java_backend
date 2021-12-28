package com.example.demo.security;


import com.example.demo.filter.CustomerAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()// 認證機制設置
                .antMatchers("/login","/twitter/login","/twitter/callback").permitAll() // 不需認證
                .anyRequest()
                .authenticated()// 除了以上的 URL 外, 都需要認證才可以訪問
                .and()
                .httpBasic()
                .and()
                // 1
                .formLogin()
                .loginPage("/twitter/login")
                // 2
                .loginProcessingUrl("/twitter/login")
                // 3
                .defaultSuccessUrl("/index")
                .permitAll();
        http.addFilter(new CustomerAuthenticationFilter(authenticationManagerBean()));

        http.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
