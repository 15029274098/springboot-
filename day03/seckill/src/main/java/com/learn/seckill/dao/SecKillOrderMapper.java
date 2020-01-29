package com.learn.seckill.dao;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
public interface SecKillOrderMapper {

    /**
     *  查询秒杀订单信息
     * @param userId 用户id
     * @param goodsId 商品id
     * @return 秒杀订单信息
     */
    SeckillOrder getSeckillOrderByUseIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);


    /**
     * 查询订单信息
     * @param userId 用户id
     * @param goodsId 商品id
     * @return 订单信息
     */
   OrderInfo getOrderInfoByUseIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    /**
     * 写入秒杀订单
     * @param seckillOrder 秒杀订单
     */

   void insertSecKillOrder(SeckillOrder seckillOrder);

   /**
     * 写入订单
     * @param orderInfo 订单信息
     */

   void insertOrderInfo(OrderInfo orderInfo);
}
