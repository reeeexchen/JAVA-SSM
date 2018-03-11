package com.imooc.seckill.service;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.Seckill;
import com.imooc.seckill.exception.RepeatKillException;
import com.imooc.seckill.exception.SeckillCloseException;
import com.imooc.seckill.exception.SeckillException;

import java.util.List;

/*
 * 该接口中前面两个方法返回的都是跟我们业务相关的对象
 * 而后两个方法返回的对象与业务不相关
 * 这两个对象我们用于封装service和web层传递的数据
 * 方法的作用我们已在注释中给出
 * 相应在的dto包中创建Exposer.java，用于封装秒杀的地址信息
 * */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);


	/**
	 * 秒杀开启时，输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀操作
	 * 有可能失败，有可能成功，所以要抛出我们允许的异常
	 * 失败就抛出一个我们允许的异常(重复秒杀异常、秒杀结束异常)
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException,RepeatKillException,SeckillCloseException;
}
