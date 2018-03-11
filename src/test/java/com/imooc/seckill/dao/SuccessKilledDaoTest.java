package com.imooc.seckill.dao;

import com.imooc.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author:REX
 * @Date: Create in 16:36 2018/3/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

	@Resource
	private SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() throws Exception{
		long seckillId = 1001L;
		long userPhone = 13631226943L;
		int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
		System.out.println("insertCount = " + insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long seckillId = 1001L;
		long userPhone = 13631226943L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());

		/*
		* SuccessKilled{
		* seckillId=1000,
		* userPhone=13631226943,
		* state=-1,
		* createTime=Wed Mar 07 16:48:43 CST 2018}
		*
		* Seckill{
		* seckillId=1000,
		* name='1000元秒杀iphone6',
		* number=100,
		* stratTime=Sat Mar 03 00:00:00 CST 2018,
		* endTime=Mon Mar 05 00:00:00 CST 2018,
		* createTime=Sun Mar 04 16:56:26 CST 2018}
		* */
	}
}