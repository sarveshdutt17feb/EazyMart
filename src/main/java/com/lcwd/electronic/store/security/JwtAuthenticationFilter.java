package com.lcwd.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //Authorization
        String requestHeader = request.getHeader("Authorization");
        //Bearer 2345465sgdgsgjs
        logger.info("Header {}",requestHeader);
        String username = null;
        String token = null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer")){
            //looking good
           token= requestHeader.substring(7);
           try{
                username=this.jwtHelper.getUsernameFromToken(token);

           }catch (IllegalArgumentException ex){
                logger.info("Illegal Argument while fetching the username");
               ex.printStackTrace();
           }catch (ExpiredJwtException e){
               logger.info("Given Jwt token is expired !!");
                e.printStackTrace();
           }catch (MalformedJwtException e){
                logger.info("some changes has done in token !! Invalid Token");
           }catch (Exception e){
                e.printStackTrace();
           }
        }else{
            logger.info("Invalid Header Value");
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //fetch user detail from username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if(validateToken){
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }else{
                logger.info("validation fails !!");
            }
        }
        filterChain.doFilter(request,response);
    }
}
