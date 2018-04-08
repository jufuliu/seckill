package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2016/7/5.
 */
public interface SeckillDao {

    int reduceNumber(@Param("seckillId") long id,@Param("killTime")Date killTime);

    Seckill get(long id);

    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
