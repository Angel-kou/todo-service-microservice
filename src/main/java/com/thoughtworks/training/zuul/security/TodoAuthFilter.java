package com.thoughtworks.training.zuul.security;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.netflix.zuul.context.RequestContext;
import com.thoughtworks.training.zuul.client.UserClient;
import com.thoughtworks.training.zuul.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class TodoAuthFilter extends OncePerRequestFilter {

    public static final String KMJ = "kmj";

    @Autowired
    UserClient userClient;

    private ResponseEntity responseEntity;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("kmj--------------"+token);
        if (!StringUtils.isEmpty(token)) {

            User user = userClient.verifyTokenIsValid(token);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user,null,
                            ImmutableList.of(new SimpleGrantedAuthority("dev"),
                                    new SimpleGrantedAuthority("play")))
            );

            RequestContext.getCurrentContext()
                    .addZuulRequestHeader(HttpHeaders.AUTHORIZATION,user.getId()+":"+user.getName());
        }
//        else {
//            String json = HttpServletRequestReader.ReadAsChars(request);
//            ObjectMapper objectMapper = new ObjectMapper();
//            User user = objectMapper.readValue(json, User.class);
//            System.out.println(user);
//            responseEntity = userClient.verifyUserIsExist(user);
//
//            System.out.println(responseEntity.getStatusCode());
//            System.out.println(HttpStatus.ACCEPTED);
//
//            String backToken = "";
//            if (responseEntity.getStatusCode() == HttpStatus.ACCEPTED) {
//                backToken = JwtAuthentication.generateToken((Integer) responseEntity.getBody());
//                System.out.println("back    " + backToken);
//            }
//
//            response.getOutputStream().print(backToken);
//            return ;
//
//        }

        filterChain.doFilter(request,response);

    }



}
