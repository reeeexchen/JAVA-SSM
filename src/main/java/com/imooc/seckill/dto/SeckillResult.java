package com.imooc.seckill.dto;

/**
 * @Author:REX
 * @Date: Create in 10:23 2018/3/9
 */
//将所有的ajax请求返回类型，全部封装成json数据
public class SeckillResult<T> {

	//请求是否成功的结果
	private boolean success;

	private T data;

	private String error;

	public SeckillResult(boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	public SeckillResult(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
