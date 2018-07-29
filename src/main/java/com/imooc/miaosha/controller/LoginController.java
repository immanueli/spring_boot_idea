package com.imooc.miaosha.controller;

import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Result<Boolean> doLogin(@Validated LoginVo loginVo) {
        logger.info("参数校验:"+loginVo.toString());
//        String password = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if (StringUtils.isEmpty(password)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }else if(StringUtils.isEmpty(mobile)){
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }else if(!ValidatorUtil.isMobile(mobile)){
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }else{
//
//        }
        // 登录
        CodeMsg codeMsg = miaoShaUserService.login(loginVo);
        if (codeMsg.getCode() == 0){
            return Result.success(true);
        }else{
            return Result.error(codeMsg);
        }
    }
}
