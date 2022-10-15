package org.shop.security.config;

import org.shop.security.filter.AuthenticationFilter;
import org.shop.service.core.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .addFilterBefore(new AuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/online/shop/user").permitAll()
                .antMatchers(HttpMethod.GET, "/online/shop/products/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/online/shop/categories/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/online/shop/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/online/shop/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/online/shop/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/online/shop/products/**").hasRole("ADMIN")
                .antMatchers("/online/shop/orders/**").hasRole("ADMIN")
                .antMatchers("/online/shop/order").hasRole("USER")
                .and()
                .build();
    }
}
