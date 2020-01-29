package com.learn.seckill.vo;



import com.learn.seckill.domain.Goods;

import java.util.Date;

/**
 * '.'
 *
 * @author Administrator
 * @date 2019/8/31 0031 16:56
 */
public class GoodsVo extends Goods {

  private Double seckillGoodsPrice;
  private Integer stockCount;
  private Date startDate;
  private Date endDate;

  public Double getSeckillGoodsPrice() {
    return seckillGoodsPrice;
  }

  public void setSeckillGoodsPrice(Double seckillGoodsPrice) {
    this.seckillGoodsPrice = seckillGoodsPrice;
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

}
