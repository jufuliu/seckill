package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 配置spring 和junit整合，junit 启动时加载springIoc容器
 */
@RunWith(SpringJUnit4ClassRunner.class)

//告诉junit spring 配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int m=successKilledDao.insertSuccessKilled(1000L,18612965670L);
        SuccessKilled s=successKilledDao.queryByIdWithSeckill(1000L,18612965670L);
        System.out.println(s.getUserPhone() +"=="+m);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        SuccessKilled s=successKilledDao.queryByIdWithSeckill(1000L,18612965670L);
        System.out.println(s.getUserPhone());

    }

}