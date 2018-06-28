package com.imooc.miaosha.controller;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试测试
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

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
}
