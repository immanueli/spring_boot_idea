package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoShaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhangch
 * @create 2018-06-28
 * @desc
 **/
@Repository("miaoShaUserDao")
@Mapper
public interface MiaoShaUserDao {

    @Select("select * from miaosha_user where id = #{id} ")
    MiaoShaUser getUserById(@Param("id") Long id);


}
