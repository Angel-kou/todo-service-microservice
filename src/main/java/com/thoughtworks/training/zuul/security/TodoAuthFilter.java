package com.thoughtworks.training.zuul.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.netflix.zuul.context.RequestContext;
import com.thoughtworks.training.zuul.client.UserClient;
import com.thoughtworks.training.zuul.dto.User;
import com.thoughtworks.training.zuul.utils.HttpServletRequestReader;
import com.thoughtworks.training.zuul.utils.JwtAuthentication;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.isEmpty(token)) {

            Claims claims = JwtAuthentication.validateToken(token);
            Integer id = JwtAuthentication.getUserId(claims);

            User user = userClient.verifyTokenIsValid(id);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user,null,
                            ImmutableList.of(new SimpleGrantedAuthority("dev"),
                                    new SimpleGrantedAuthority("play")))
            );

            RequestContext.getCurrentContext()
                    .addZuulRequestHeader(HttpHeaders.AUTHORIZATION,user.getId()+":"+user.getName());
        }
        else {

            System.out.println("------request.getServletPath()-------" + request.getServletPath());
            if(request.getServletPath().equals("/api/login")) {
                System.out.println("-------------kmj");
                String json = HttpServletRequestReader.ReadAsChars(request);
                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(json, User.class);

                User temp  = userClient.verifyUserIsExist(user);


                String backToken = "";
                if(temp.getId() != 0) {

                    backToken = JwtAuthentication.generateToken(temp.getId());
                    response.getOutputStream().print(backToken);

                }else{
                    response.getOutputStream().print("user is not exist");
                }

                return;
            }
        }

        filterChain.doFilter(request,response);

    }



}
