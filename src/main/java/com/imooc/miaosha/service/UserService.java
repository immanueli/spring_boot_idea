package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangch
 * @create 2018-06-28
 * @desc
 **/
@Service("userService")
public class UserService {
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;


    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public boolean dbTx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("2222");
        int i = userDao.insert(u1);
        User u2 = new User();
        u2.setId(1);
        u2.setName("1111");
        int i2 = userDao.insert(u2);
        return true;
    }
}
