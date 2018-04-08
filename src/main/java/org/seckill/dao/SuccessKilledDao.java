package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by liu on 2016/7/5.
 */
public interface SuccessKilledDao {
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone")long userPhone);

    SuccessKilled queryByIdWithSeckill (@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
