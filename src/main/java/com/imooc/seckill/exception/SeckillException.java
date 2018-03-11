package com.imooc.seckill.exception;

/**
 * 秒杀相关的所有业务异常
 * @Author:REX
 * @Date: Create in 20:39 2018/3/7
 */
public class SeckillException extends RuntimeException{


	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}
}
