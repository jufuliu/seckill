package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring 和junit整合，junit 启动时加载springIoc容器
 */
@RunWith(SpringJUnit4ClassRunner.class)

//告诉junit spring 配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        int m=seckillDao.reduceNumber(1000L,new Date());
        Seckill seckill=seckillDao.get(1000L);
        System.out.println(seckill.getNumber());
    }

    @Test
    public void get() throws Exception {
        Seckill seckill=seckillDao.get(1000);
        System.out.print(seckill.toString());
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> list=seckillDao.queryAll(0,100);
        for(Seckill seckill :list){
            System.out.println(seckill.getName());
        }
    }

}