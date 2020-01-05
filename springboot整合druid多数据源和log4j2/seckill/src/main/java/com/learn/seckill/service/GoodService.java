package com.learn.seckill.service;

import com.learn.seckill.dao.GoodMapper;
import com.learn.seckill.domain.Goods;
import com.learn.seckill.mutildatasource.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @DataSource("slave")
    public List<Goods> selectAllGood() {
        return goodMapper.selectAllGood();
    }
}
