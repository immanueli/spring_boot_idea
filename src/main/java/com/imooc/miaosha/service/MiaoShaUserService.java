package com.imooc.miaosha.service;


import com.imooc.miaosha.dao.MiaoShaUserDao;
import com.imooc.miaosha.domain.MiaoShaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("miaoShaUserService")
public class MiaoShaUserService {
    @Autowired
    @Qualifier("miaoShaUserDao")
    private MiaoShaUserDao miaoShaUserDao;

    public MiaoShaUser getUserById (Long id){
        return miaoShaUserDao.getUserById(id);
    }

    public boolean login(LoginVo loginVo) {
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
        System.out.println(dbPass);
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!dbPass.equals(calcPass)){
            throw new GlobalException ( CodeMsg.PASSWORD_ERROR);
        }
        return true;
    }
}
