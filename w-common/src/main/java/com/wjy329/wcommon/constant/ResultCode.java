package com.wjy329.wcommon.constant;

public enum ResultCode {
	
	//返回结果
	OK("1","OK"),
	ERROR("2","ERROR");
	
	
	private String code;
	private String msg;
	
	private ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
