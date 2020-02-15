package com.learn.seckill.vo;

import com.learn.seckill.domain.SecKillUser;

/** '.'
 * @author Administrator
 * @date 2019/9/11 0011 23:21
 */
public class GoodsDetailVo {

  private int seckillStatus;
  private int remainSeconds;
  private GoodsVo goodsVo;

  public SecKillUser getSeckillUser() {
    return seckillUser;
  }

  public void setSeckillUser(SecKillUser seckillUser) {
    this.seckillUser = seckillUser;
  }

  private SecKillUser seckillUser;


  public int getSeckillStatus() {
    return seckillStatus;
  }

  public void setSeckillStatus(int seckillStatus) {
    this.seckillStatus = seckillStatus;
  }

  public int getRemainSeconds() {
    return remainSeconds;
  }

  public void setRemainSeconds(int remainSeconds) {
    this.remainSeconds = remainSeconds;
  }

  public GoodsVo getGoodsVo() {
    return goodsVo;
  }

  public void setGoodsVo(GoodsVo goodsVo) {
    this.goodsVo = goodsVo;
  }
}
