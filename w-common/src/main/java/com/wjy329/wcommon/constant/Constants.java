package com.wjy329.wcommon.constant;


/**
 * @Date: 2018.12.7
 * @author: wjy329
 * 功能描述:<br/>
 */
public class Constants {
	
	//正常返回
	public static final String SHELL_RESULT_OK ="OK";
	
	//异常返回
	public static final String SHELL_RESULT_FAILED ="FAILED";
	
	//调度任务的间隔时间,单位ms,设置20s扫描一次
	public static final long JOB_SCHEDULED_FIXED_RATE = 20000l;
	
	//线程池大小
	public static final Integer JOB_POOL_SIZE = 10;

	//登录用户
	public static final String LOGIN_USER ="user";
	
	//登录地址
	public static final String LOGIN_URL ="loginUrl";
	
	//授权用户
	public static final String AUTH_ROLE = "auth";
	
	//管理员角色名称
	public static final String ADMIN_ROLE = "admin";
	
	//或者的情况,允许多个用户中，一个用户访问
	public static final String OR_ROLE = "orRole";
	
	//shiro过滤器的原则， 这个是与 
	public static final String SHIRO_ROLES = "authc,roles[%s]";

	//或角色的关系
	public static final String SHIRO_OR_ROLES = "authc,"+OR_ROLE+"[%s]";
	
	//过滤的url设定
	public static final String SHIRO_FILTER = "/%s/**";
	
	//默认图标
	public static final String DEFAULT_ICON = "&#xe615;";
	
	//上传文件路径
	public static final String UPLOAD_PATH = "upload";

	//管理员默认id
	public static final Integer ADMIN_ID = 1;

	//跟菜单的标识
	public static final Integer ROOT_MENU_FLAG = 0;


	
}
