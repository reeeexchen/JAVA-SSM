package com.imooc.seckill.dao;

import com.imooc.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author:REX
 * @Date: Create in 23:05 2018/3/6
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	//注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao;

	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
		/*
		*
		* 1000元秒杀iphone6
			Seckill
			{seckillId=1000,
			name='1000元秒杀iphone6',
			number=100,
			stratTime=null,
			endTime=Mon Mar 05 00:00:00 CST 2018,
			createTime=Sun Mar 04 16:56:26 CST 2018}
		* */
	}

	@Test
	public void testQueryAll() {
//		Caused by: org.apache.ibatis.binding.BindingException:
// 		Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
//		List<Seckill> queryAll(int offet,int limit);
//		java没有保存形参的记录：
//		queryAll(int offet,int limit) -> queryAll(arg0,arg1)
		List<Seckill> seckills = seckillDao.queryAll(0,100);
		for(Seckill seckill : seckills){
			System.out.println(seckill);
		}
	}

	@Test
	public void testReduceNumber() {

		/*
		*
		* 16:26:50.237 [main] DEBUG org.mybatis.spring.transaction.SpringManagedTransaction -
		* JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@5bd1ceca]
		* will not be managed by Spring
		* 16:26:50.260 [main] DEBUG com.imooc.seckill.dao.SeckillDao.reduceNumber -
		* ==>  Preparing: -- 具体SQL语句
		* UPDATE seckill SET number = number - 1
		* WHERE seckill_id = ?
		* AND start_time <= ? AND end_time >= ? AND number > 0;
		* 16:26:50.322 [main] DEBUG com.imooc.seckill.dao.SeckillDao.reduceNumber -
		* ==> Parameters: 1000(Long), 2018-03-07 16:26:49.883(Timestamp),
		* 2018-03-07 16:26:49.883(Timestamp)
		* 16:26:50.393 [main] DEBUG com.imooc.seckill.dao.SeckillDao.reduceNumber -
		* <==    Updates: 0
		* */

		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000l,killTime);
		System.out.println("updateCount = "  + updateCount);
	}

}
