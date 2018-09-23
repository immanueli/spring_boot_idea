package com.imooc.miaosha.service;


import com.imooc.miaosha.dao.MiaoShaUserDao;
import com.imooc.miaosha.domain.MiaoShaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoShaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("miaoShaUserService")
public class MiaoShaUserService {

    public static final String COOKIE_NAME_TKOEN = "token";

    @Autowired
    @Qualifier("miaoShaUserDao")
    private MiaoShaUserDao miaoShaUserDao;

    @Autowired
    private RedisService redisService;

    public MiaoShaUser getUserById (Long id){
        return miaoShaUserDao.getUserById(id);
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo) {
        if (loginVo == null){
            throw new GlobalException (CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断是否存在
        MiaoShaUser user = getUserById(Long.parseLong(mobile));
        if (user == null){
            throw new GlobalException (CodeMsg.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!dbPass.equals(calcPass)){
            throw new GlobalException ( CodeMsg.PASSWORD_ERROR);
        }
        String uuidToken = UUIDUtil.uuid();
        // 生成cookie
        addCookie(user,uuidToken,response);
        return true;
    }

    public MiaoShaUser getByToken(String token,HttpServletResponse response) {
        if (StringUtils.isEmpty(token)) return null;
        // 延长有效期
        MiaoShaUser miaoShaUser = redisService.get(MiaoShaUserKey.token, token, MiaoShaUser.class);
        if (miaoShaUser != null) addCookie(miaoShaUser,token,response);
        return miaoShaUser;
    }

    // 生成cookie
    private void addCookie(MiaoShaUser user,String token,HttpServletResponse response){
        // 存放在redis 服务器中
        redisService.set(MiaoShaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TKOEN,token);
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
