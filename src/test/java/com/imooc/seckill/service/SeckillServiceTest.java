package com.imooc.seckill.service;

import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.Seckill;
import com.imooc.seckill.exception.RepeatKillException;
import com.imooc.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author:REX
 * @Date: Create in 21:34 2018/3/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:spring/spring-service.xml",
		"classpath:spring/spring-dao.xml"})
public class SeckillServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void getSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
		//Closing non transactional SqlSession
	}

	@Test
	public void getById() {
		long id = 1000l;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}",seckill);
	}

	//集成测试代码完整逻辑,注意可重复执行
	@Test
	public void SeckillLogic() throws Exception{
		//测试时，秒杀seckillId:1000已开启
		long id = 1000l;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			logger.info("exposer={}",exposer);
			long phone = 13476191878L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
				logger.info("result={}",execution);
			}catch (RepeatKillException e){
				logger.error(e.getMessage());
			}catch (SeckillCloseException e){
				logger.error(e.getMessage());
			}
		}else{
			//警告：秒杀未开启
			logger.warn("exposer={}",exposer);
		}
		/*
		* 可知开启了id为1000的商品的秒杀，并给我们输出了该商品的秒杀地址
		* exposer=Exposer{
		* exposed=true,
		* md5='df9fcbe054242ca5fb93917e35592b1f',
		* seckillId=1000,
		* now=0, start=0, end=0}
		 * */
	}

	@Test
	public void executeSeckill() {
		//传入该地址让用户得到才能判断该用户是否秒杀到该地址的商品
		long id = 1000l;
		long phone = 13476191877L;
		String md5 = "df9fcbe054242ca5fb93917e35592b1f";
		try {
			SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
			logger.info("result={}",execution);
		}catch (RepeatKillException e){
			logger.error(e.getMessage());
		}catch (SeckillCloseException e){
			logger.error(e.getMessage());
			/*
			* 这样再测试该方法，junit便不会再在控制台中报错
			* 而是认为这是我们系统允许出现的异常。
			* 由上分析可知，第四个方法只有拿到了第三个方法暴露的秒杀商品的地址后
			* 才能进行测试，也就是说只有在第三个方法运行后才能运行测试第四个方法，
			* 而实际开发中我们不是这样的，需要将第三个测试方法和第四个方法
			* 合并到一个方法从而组成一个完整的逻辑流程:
			* */
		}

		/*
		* result=SeckillExecution{
		* seckillId=1000,
		* state=1, stateInfo='秒杀成功',
		* successKilled=SuccessKilled{
		* seckillId=1000, userPhone=13476191877,
		* state=0, createTime=Thu Mar 08 22:17:39 CST 2018}}
		 * */

		//此时再次执行该方法，控制台报错，因为用户重复秒杀了。
		//我们应该在该测试方法中添加try catch,将程序允许的异常包起来而不去向上抛给junit
	}
}