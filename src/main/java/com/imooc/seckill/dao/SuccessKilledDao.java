package com.imooc.seckill.dao;

import com.imooc.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * @Author:REX
 * @Date: Create in 17:23 2018/3/4
 */
public interface SuccessKilledDao {

	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return 插入的行数
	 */

	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

	/**
	 * 根据ID查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
