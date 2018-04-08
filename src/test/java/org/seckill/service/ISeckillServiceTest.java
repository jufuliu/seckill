package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liu on 2016/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class ISeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        for (Seckill seckill : list) {
            logger.debug(seckill.getName());
        }

    }

    @Test
    public void getById() throws Exception {

        Seckill seckill = seckillService.getById(1000);
        logger.debug(seckill.getName());
    }

    //    秒杀逻辑测试
    @Test
    public void testSeckillLogic() throws Exception {

        long seckillId = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            long userPhone = 18612965670L;
            try {
                SeckillExcution seckillExcution = seckillService.excuteSeckill(exposer.getSeckillId(), userPhone, exposer.getMd5());
                logger.debug("{}", seckillExcution);
            } catch (SeckillException e) {
                logger.debug(e.getMessage());
            }
        } else {
            logger.debug("{}", exposer);
        }
    }


}