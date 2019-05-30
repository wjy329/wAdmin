package com.wjy329.wcommon.constant;
public enum UserStatus {
	//返回结果
	LOCK(0,"用户禁用了"),
	ACTIVE(1,"正常用户");
	
	/** 
	* @description 用户状态
	* @param  
	* @return  
	* @author wjy329
	* @Date 2018/12/31 
	*/ 
	public static UserStatus getByCode(Integer code){
		UserStatus [] status = UserStatus.values();
		for(UserStatus statu:status){
			if(statu.getCode().equals(code)){
				return statu;
			}
		}
		return null;
	}
	
	private UserStatus(Integer code, String descp) {
		this.code = code;
		this.descp = descp;
	}
	private Integer code;
	private String descp;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
}
