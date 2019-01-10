package com.nikola2934.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
//public class MyCorsFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//            FilterChain fc) throws ServletException, IOException {
//        response.addHeader("Access-Control-Allow-Credentials", "true");
////        response.addHeader("Access-Control-Allow-Origin","http://localhost:4200");
////
////        if (request.getHeader("Access-Control-Request-Method") != null
////                && "OPTIONS".equals(request.getMethod())) {
////            // CORS "pre-flight" request
////            response.addHeader("Access-Control-Allow-Methods",
////                    "GET, POST");
////            response.addHeader("Access-Control-Allow-Headers",
////                    "X-Requested-With,Origin,Content-Type, Accept");
////        }
////        fc.doFilter(request, response);    
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token, Cache-Control");
//        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
//
//        if ("OPTIONS".equals(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            fc.doFilter(request, response);
//        }
//
//    }
//
//}
