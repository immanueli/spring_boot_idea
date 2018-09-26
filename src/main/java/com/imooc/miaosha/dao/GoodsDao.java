package com.imooc.miaosha.dao;

import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("goodsDao")
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id ")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id " +
            "where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(Long goodsId);
}
