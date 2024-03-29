package com.training.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authentication")
                .usernameParameter("userName")
                .passwordParameter("password")
                .defaultSuccessUrl("/showtime")
                .failureUrl("/login?action=FAIL")
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .authorizeHttpRequests()
                .antMatchers("/login", "/register")
                .permitAll()
                .antMatchers("/js/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/showtime/**").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/cinemaRoom/**", "/employee/**", "/movie/**", "/promotion/**").hasRole("ADMIN")
                .antMatchers("/ticketBookingManagement/**").hasAnyRole("EMPLOYEE", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
//                http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    }

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
