package com.thoughtworks.training.kmj.todoservice.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.AbstractEndpointMvcAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private TodoAuthFilter todoAuthFilter;

    @Autowired
    private List<AbstractEndpointMvcAdapter<? extends Endpoint<?>>> actuatorEndpoints;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users","/login").permitAll()
                .antMatchers( "/hystrix*/**", "/webjars/**","/*.stream/**").permitAll()
                .antMatchers(actuatorEndpoints.stream().map(AbstractEndpointMvcAdapter::getPath).toArray(String[]::new)).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(todoAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint);
    }


}
