package com.imooc.seckill.service.Impl;

import com.imooc.seckill.dao.SeckillDao;
import com.imooc.seckill.dao.SuccessKilledDao;
import com.imooc.seckill.dto.Exposer;
import com.imooc.seckill.dto.SeckillExecution;
import com.imooc.seckill.entity.Seckill;
import com.imooc.seckill.entity.SuccessKilled;
import com.imooc.seckill.enums.SeckillStatEnum;
import com.imooc.seckill.exception.RepeatKillException;
import com.imooc.seckill.exception.SeckillCloseException;
import com.imooc.seckill.exception.SeckillException;
import com.imooc.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author:REX
 * @Date: Create in 20:57 2018/3/7
 */
//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

	//日志对象
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//注入Service依赖
	@Autowired //@Resource,@Inject
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	//加入一个混淆字符串(秒杀接口)的salt，
	//为了我避免用户猜出我们的md5值，值任意给，越复杂越好
	private final String slat = "saery234//(&*^&*312156[r[#@$%";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		//说明查不到这个秒杀产品的记录
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		//如果秒杀没有开启
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//系统当前时间
		Date nowTime = new Date();

		if (startTime.getTime() > nowTime.getTime()
				|| endTime.getTime() < nowTime.getTime()) {
			return new Exposer(false, seckillId,
					nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}

		//秒杀开启，返回秒杀商品的id、用给接口加密的md5(不可逆)
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Transactional
	/*
	* 使用注解控制事物方法的优点：
	* 1、开发团队达成一致约定，明确标注事务方法的编程风格
	* 2、保证事务方法的执行时间尽可能短，
	* 	不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
	* 3、不是所有的方法都需要事务，如果只有一条修改操作，只读操作，不需要事务控制
	* */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑:减库存、记录购买行为
		Date nowTime = new Date();
		try {
			//减库存
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				//没有更新到记录,秒杀结束
				throw new SeckillCloseException("seckill is closed");
			} else {
				//减库存成功，记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//唯一：seckillId，userPhone
				if (insertCount <= 0) {
					//重复秒杀
					throw new RepeatKillException("seckill repeated");
				} else {
					//秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所有编译期异常 转化为运行期异常
			throw new SeckillException("seckill inner error : " + e.getMessage());
		}
	}
}
