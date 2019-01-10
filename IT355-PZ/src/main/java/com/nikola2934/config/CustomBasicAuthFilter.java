package com.nikola2934.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component()
public class CustomBasicAuthFilter extends BasicAuthenticationFilter {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomBasicAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void onSuccessfulAuthentication(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Authentication authResult) {
        // Do what you want here
        System.out.println("successfull");
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        if(request.getRequestURI().equals("/login")){
            System.out.println("cao CUCUUCUUCUU");
//            redirectStrategy.sendRedirect(request, response, "/loginfail");
        }
    }

}
