package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoShaUser;
import com.imooc.miaosha.redis.MiaoShaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 商品
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    @Qualifier("goodsService")
    private GoodsService goodsService;

    /**
     * @return toList
     */
    @RequestMapping("/toList")
    public String toList(Model model, MiaoShaUser user) {
        model.addAttribute("user",user);
        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        return "goodsList";
    }

    /**
     * 商品详情页
     * @param model
     * @param response
     * @param cookieToken
     * @param paramToken
     * @return
     */
    @RequestMapping("/toDetail")
    public String toDetail(Model model, HttpServletResponse response,@CookieValue (value = MiaoShaUserService.COOKIE_NAME_TKOEN,required = false) String cookieToken,
                         @RequestParam(value = MiaoShaUserService.COOKIE_NAME_TKOEN,required = false) String paramToken) {

        return "goodsDetail";
    }


}
