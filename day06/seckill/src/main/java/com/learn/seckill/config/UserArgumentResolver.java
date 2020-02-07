package com.learn.seckill.config;

import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.service.SecKillUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Configuration
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private SecKillUserService secKillUserService;

    /**
     * 对使用SecKillUser参数的方法
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == SecKillUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        //从请求参数中获取token,针对移动端
        String paramToken = request.getParameter(SecKillUserService.COOKIE_NAME_TOKEN);
        //从cookie中取token，针对pc端
        String cookieToken = getCookieToken(request, SecKillUserService.COOKIE_NAME_TOKEN);
        System.out.println(cookieToken+"---------------------");
        // 返回的对象为空，controller有使用SecKillUser参数的，需要对其判空处理防止，没有登录而访问除了登录以外的界面
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        SecKillUser secKillUser = secKillUserService.getByToken(response, token);
        return secKillUser;
    }

    private String getCookieToken(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieNameToken)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
