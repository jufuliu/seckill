package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2016/7/9.
 */
@Service
public class SeckillServiceImpl implements ISeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String slat="oqwijg908309r81348(^&^-304m";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,100);
    }

    public Seckill getById(long id) {
        return seckillDao.get(id);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=seckillDao.get(seckillId);
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime() ){
            return new Exposer(false,seckillId);
        }
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /*
    * 使用注解控制事务的优点：
    * 1：开发团队达成一致约定，明确标注事务方法的编程风格；
    * 2：保证事务方法的执行时间尽可能短，不要穿插其它的网络操作，如rpc/http,或者可以将其它操作剥离到事务外部
    * 3：不是所有方法都需要事务，如只有一条修改操作，只读操作不需要事务
    * */
    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5==null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        Date now =new Date();
        try {
            int updateCount=seckillDao.reduceNumber(seckillId,now);
            if(updateCount<=0){
                throw new SeckillCloseException("秒杀关闭");
            }else{
                int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if(insertCount<=0){
                    throw new RepeatKillException("重复秒杀");
                }else{
                    SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExcution(seckillId,SeckillStateEnum.SUCCESS);
                }
            }
        } catch (SeckillCloseException e) {
            logger.error(e.getMessage());
           throw  e;
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
            throw  e;
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
