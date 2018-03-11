package com.imooc.seckill.exception;

/**
 * 秒杀关闭异常
 * 当秒杀结束时用户还要进行秒杀就会出现这个异常
 * @Author:REX
 * @Date: Create in 20:37 2018/3/7
 */
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}
}
