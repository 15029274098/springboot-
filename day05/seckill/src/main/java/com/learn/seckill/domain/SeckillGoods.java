package com.learn.seckill.domain;

import java.util.Date;

/**
 * @author Administrator
 * @date 2019/8/31 0031 15:05
 */
public class SeckillGoods {

  private Long id;
  private Long goodsId;
  private Double seckillGoodsPrice;
  private Integer stockCount;
  private Date startDate;
  private Date endDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Long goodsId) {
    this.goodsId = goodsId;
  }

  public Integer getStockCount() {
    return stockCount;
  }

  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Double getSeckillGoodsPrice() {
    return seckillGoodsPrice;
  }

  public void setSeckillGoodsPrice(Double seckillGoodsPrice) {
    this.seckillGoodsPrice = seckillGoodsPrice;
  }
}
