package org.lavender.library.annotation;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.lavender.library.exception_advice.BusinessException;
import org.lavender.library.jwt.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * a happy start
 *
 * @author thg
 * @since 2025/1/3 17:41
 * @version 1.0.0
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        Auth annotation;
        if(handler instanceof HandlerMethod){
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
        }else{
            return true;
        }
        if(annotation == null){
            return true;
        }
        //获取token
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        if(StringUtils.isEmpty(token)){
            throw new BusinessException(600,"token不能为空!");
        }
        //判断是否过期
        Boolean expired = jwtUtils.isTokenExpired(token);
        if(expired){
            throw new BusinessException(600,"token过期!");
        }
        //解析token
        String username = jwtUtils.getUsernameFromToken(token);
        if(StringUtils.isEmpty(username)){
            throw new BusinessException(600,"token验证失败!");
        }
        return true;
    }
}

