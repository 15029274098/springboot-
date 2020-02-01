package com.learn.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class SecKillUser {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String headImage;
private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
