package com.learn.seckill.dao;

import com.learn.seckill.domain.Goods;

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
    List<Goods> selectAllGood();
}
