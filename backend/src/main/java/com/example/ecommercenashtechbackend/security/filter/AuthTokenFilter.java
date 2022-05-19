package com.example.ecommercenashtechbackend.security.filter;

import com.example.ecommercenashtechbackend.exception.ExceptionResponse;
import com.example.ecommercenashtechbackend.security.UserDetailService;
import com.example.ecommercenashtechbackend.security.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException {
//        String path = request.getRequestURI();
//        List<String> acceptURL = List.of("/api/admin", "/api/reviews/create", "/user");
//        boolean isAcceptURL = checkAcceptURL(acceptURL, path);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().timestamp(new Date())
                .detail(request.getRequestURI()).status(HttpStatus.FORBIDDEN.value()).build();
        boolean hasError = false;
//        if (isAcceptURL) {
            String jwt = parseJwt(request);
            if (jwt != null) {
                try {
                    jwtUtil.validateToken(jwt);
                    String username = jwtUtil.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    exceptionResponse.setMessage(e.getMessage());
                    hasError = true;
                }
            }
//            } else {
//                exceptionResponse.setMessage("Missing token");
//                hasError = true;
//            }
//        }

        if(hasError) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(convertObjectToJson(exceptionResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    public boolean checkAcceptURL(List<String> acceptURL, String path) {
        boolean flag = false;
        for (String url : acceptURL) {
            if (path.contains(url)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

