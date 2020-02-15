package com.learn.seckill.access;


import com.alibaba.fastjson.JSON;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.redis.AccessKey;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.SecKillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


/**
 * @author Administrator
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            SecKillUser user = getUser(request, response);
            //将用户保存到 ThreadLocal中
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            //获取方法注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            //如果没有不做任何限制
            if (accessLimit == null) {
                return true;
            }
            //获取注解定义的几个参数
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    //如果没有登录则返回错误信息，需要自己输出
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + user.getId();
            }
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak, key, Integer.class);
            if (count == null) {
                redisService.set(ak, key, 1);
            } else if (count < maxCount) {
                redisService.incr(ak, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

  /**
   * 取用户信息
   * @param request
   * @param response
   * @return
   */
    private SecKillUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(SecKillUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, SecKillUserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
