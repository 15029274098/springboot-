package com.learn.seckill.service;

import com.learn.seckill.dao.SecKillUserMapper;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.exception.GlobalException;
import com.learn.seckill.mutildatasource.DataSource;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.redis.SeckillUserKey;
import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.util.Md5Util;
import com.learn.seckill.util.UUIDUtil;
import com.learn.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SecKillUserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    private SecKillUserMapper secKillUserMapper;
    @Autowired
    private RedisService redisService;

    @DataSource
    public List<SecKillUser> selectAllUser() {
        return secKillUserMapper.selectAllUser();
    }

    @DataSource("slave")
    public SecKillUser selectOneUserByMobile(String username) {
        return secKillUserMapper.selectOneUserByMobile(username);
    }

    @DataSource
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(SecKillUser secKillUser) {
        secKillUserMapper.insertUser(secKillUser);
    }


    public boolean login(HttpServletResponse httpServletResponse, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        SecKillUser secKillUser = selectOneUserByMobile(mobile);
        if (secKillUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXISIT);
        }
        //获取数据库随机盐值
        String salt = secKillUser.getSalt();
        //判断前后端两次MD5的时候密码是否一致
        String inputPassword = Md5Util.formPassToDbPass(password, salt);
        // 注册时候生成的密码，也就是注册的时候生成的密码
        String dbPassword = secKillUser.getPassword();
        if (!inputPassword.equals(dbPassword)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(httpServletResponse, secKillUser, token);
        return true;
    }

    private void addCookie(HttpServletResponse httpServletResponse, SecKillUser secKillUser, String token) {
        redisService.set(SeckillUserKey.token, token, secKillUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public SecKillUser getByToken(HttpServletResponse httpServletResponse, String token) {
        /**
         * token为空说明登录失效了
         */
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SecKillUser secKillUsers = redisService.get(SeckillUserKey.token, token, SecKillUser.class);
        /**
         * 延长session和cookie的有效期
         */
        if (secKillUsers != null) {
            addCookie(httpServletResponse, secKillUsers, token);
        }
        return secKillUsers;
    }
}
