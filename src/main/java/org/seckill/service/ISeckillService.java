package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by liu on 2016/7/9.
 * 站在使用者的角度设计接口
 * 三个方面：方法定义粒度、参数、返回类型（return 类型/异常）
 */
public interface ISeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long id);

    /*
    *秒杀开启时输出秒杀接口地址，
    * 否则输出当前系统时间
    * */
    Exposer exportSeckillUrl(long id);

    /*
    * 执行秒杀操作
    * */
    SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
