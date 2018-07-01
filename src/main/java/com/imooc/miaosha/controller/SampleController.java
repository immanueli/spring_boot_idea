package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.KeyPrefix;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试测试
 * 测试测试
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Hello Spring Boot");
        return "hello";
    }

    /**
     * 成功时候的调用
     * @return
     */
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello world");
    }

    /**
     * 失败
     * @param model
     * @return
     */
    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(Model model) {

        return Result.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 测试数据库
     * @return
     */
    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> get() {
        User user = userService.getUserById(1);
        return Result.success(user);
    }
    /**
     * 测试事务
     * @return
     */
    @RequestMapping("/db/Tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        boolean b = userService.dbTx();
        return Result.success(b);
    }

    /**
     * 测试redisGet
     * @return
     */
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User user = redisService.get(UserKey.getById,""+1, User.class);
        return Result.success(user);
    }

    /**
     * 测试redisSet
     * @return
     */
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById,""+1,user);
        return Result.success(true);
    }
}
