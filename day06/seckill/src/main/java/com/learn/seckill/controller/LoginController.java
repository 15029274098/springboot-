package com.learn.seckill.controller;

import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.SecKillUserService;
import com.learn.seckill.util.ValidatorUtil;
import com.learn.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Administrator
 */
@Controller
@Slf4j
public class LoginController {
    @Autowired
    private SecKillUserService secKillUserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 校验密码手机号，mobile不存在
     *
     * @return
     */
    @PostMapping("/login/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse httpServletResponse, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
//        String mobile = loginVo.getMobile();
//        String password = loginVo.getPassword();
//        if (StringUtils.isEmpty(password)) {
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        CodeMsg codeMsg = secKillUserService.login(loginVo);
//        if (codeMsg.getCode() == 0) {
//            return Result.success(true);
//        } else {
//            return Result.error(codeMsg);
//        }
        secKillUserService.login(httpServletResponse, loginVo);
        return Result.success(true);
    }
}
