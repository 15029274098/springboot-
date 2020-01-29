package com.learn.seckill.dao;

import com.learn.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
public interface GoodMapper {
    /**
     * 查询所有商品
     *
     * @return 商品
     */
    List<GoodsVo> selectAllGood();
    /**
     * 通过id查询商品
     *
     * @return 商品
     */
    GoodsVo selectSeckillGoodById(@Param("goodsId")long goodsId);

    int reduceStock(GoodsVo goodsVo);
}
