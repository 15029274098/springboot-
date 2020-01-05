package com.learn.seckill.domain;


import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsDetail;
    private double goodsPrice;
    private String goodsImg;
    private Integer goodsStock;
}
