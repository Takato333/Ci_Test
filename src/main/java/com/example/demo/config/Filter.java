package com.example.demo.config;

import com.example.demo.entity.Account;
import com.example.demo.exception.AuthException;
import com.example.demo.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    //truy cập ko cần token
    private final List<String> AUTH_PERMISSION =  Arrays.asList(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register",
            "/api/forgot-password"
    );

    public boolean checkIsPublicAPI(String uri){
        //uri: /api/register
        //nếu gặp những cái api ở trong list ở trên => cho phép truy cập lun => true
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //check token => false
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //check xem cái api mà người dùng yêu cầu có phải là 1 public api?

        boolean isPublicAPI = checkIsPublicAPI(request.getRequestURI());
        if(isPublicAPI){
            filterChain.doFilter(request, response);
        }else {
            String token = getToken(request);
            if (token == null) {
                //ko đc phép truy cập
                resolver.resolveException(request, response, null, new AuthException("Empty Token!"));
                return;
            }

            // => có token
            //check xem token có đúng hay ko => lấy thông tin account từ token
            Account account;
            try {
                account = tokenService.getAccountByToken(token);
            } catch (ExpiredJwtException e) {
                //response token hết hạn
                resolver.resolveException(request, response, null, new AuthException("ExpiredToken!"));
                return;
            } catch (MalformedJwtException malformedJwtException) {
                //response token sai
                resolver.resolveException(request, response, null, new AuthException("Invalid Token!"));
                return;
            }
            // => token chuẩn
            // => cho phép truy cập
            // => lưu lại thông tin account
            //account = principal
            //token = credential
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    account,
                    token,
                    account.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // token ok, cho vào
            filterChain.doFilter(request, response);
        }

    }

    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.substring(7);
    }

}
