package com.springboot.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 1. Inject dependencies
    @Autowired
    private JwtTokenProvider tokenProvider ;

    @Autowired
    private CustomUserDetailsService customUserDetailsService ;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 2. get JWT ( token ) from http request
        String token = getJWTFromRequest(request) ;
        // 3. validate token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)){
            // 4. get username from token
            String username = tokenProvider.getUsernameFromJWT(token) ;
            // 5. load user associated with token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            ) ;
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 6. set spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }

    // Implementation of step 2. ---> Bearer <accessToken>
    private String getJWTFromRequest(HttpServletRequest request){
       String bearerToken = request.getHeader("Authorization") ;
       if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
           return  bearerToken.substring(7, bearerToken.length()) ;
           // NOTE: 7 because B(1)e(2)a(3)r(4)e(5)r(6)_(7)
       }
       return null ;
    }
}
