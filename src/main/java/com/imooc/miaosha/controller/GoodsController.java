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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
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

    @Autowired
    private RedisService redisService;

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
     * @param user
     * @return
     */
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, MiaoShaUser user, @PathVariable("goodsId") Long goodsId) {
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        long startTime = goodsVo.getStartDate().getTime();
        System.out.println(startTime);
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        System.out.println(now);
        int miaoShaStatus;
        int remainSecond;
        if (now < startTime){
            miaoShaStatus = 0;
            remainSecond = (int)((startTime - now) / 1000);
        }else if(now < endTime){
            miaoShaStatus = 2;
            remainSecond = -1;
        }else{
            miaoShaStatus = 1;
            remainSecond = 0;
        }
        model.addAttribute("goods",goodsVo);
        model.addAttribute("miaoshaStatus",miaoShaStatus);
        model.addAttribute("remainSeconds",remainSecond);
        return "goodsDetail";
    }


}
