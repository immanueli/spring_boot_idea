package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhangch
 * @create 2018-06-28
 * @desc
 **/
@Repository("userDao")
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id} ")
    User getUserById(@Param("id") Integer id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    int insert(User user);
}
