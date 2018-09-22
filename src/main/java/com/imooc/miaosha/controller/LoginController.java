package com.imooc.miaosha.controller;

import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录功能
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    @Qualifier("miaoShaUserService")
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;
    /**
     * @return login
     */
    @RequestMapping("/toLogin")
    public String toLogin(Model model) {
        return "login";
    }

    /**
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        logger.info("参数校验:"+loginVo.toString());
        // 登录
        boolean b = miaoShaUserService.login(response,loginVo);
        return Result.success(b);
    }
}
