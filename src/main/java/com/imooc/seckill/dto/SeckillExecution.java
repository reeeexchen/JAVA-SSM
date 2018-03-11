package com.imooc.seckill.dto;

import com.imooc.seckill.entity.SuccessKilled;
import com.imooc.seckill.enums.SeckillStatEnum;

/**
 * 封装秒杀执行后的结果（成功/失败）
 * @Author:REX
 * @Date: Create in 20:31 2018/3/7
 */
public class SeckillExecution {

	/*
	* 用于判断秒杀是否成功
	* 成功就返回秒杀成功的所有信息(包括秒杀的商品id、秒杀成功状态、成功信息、用户明细)
	* 失败就抛出一个我们允许的异常(重复秒杀异常、秒杀结束异常)
	* */

	private long seckillId;

	//秒杀执行结果状态
	private int state;

	//状态标识
	private String stateInfo;

	//秒杀成功时，需要传递秒杀成功的对象
	private SuccessKilled successKilled;

	//秒杀成功返回所有信息
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successKilled = successKilled;
	}

	//秒杀失败
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution{" + "seckillId=" + seckillId + ", state=" + state + ", stateInfo='" + stateInfo + '\'' + ", successKilled=" + successKilled + '}';
	}
}
