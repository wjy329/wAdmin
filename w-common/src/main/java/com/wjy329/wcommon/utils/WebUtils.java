package com.wjy329.wcommon.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjy329.wcommon.constant.ResultCode;
import com.wjy329.wcommon.dto.PageInfo;
import com.wjy329.wcommon.dto.SystemPageContext;


import java.util.Map;

public class WebUtils {

	private static WebUtils utils = null;
	private WebUtils(){};
	
	
	/**
	 * @Date: 2018.12.7
	 * @author: wjy329
	 * 功能描述:双判断实例化Layui的工具包<br/>
	 * @return
	 */
	public static WebUtils getInstance(){
		if(utils == null){
			synchronized (WebUtils.class) {
				if(utils == null){
					utils = new WebUtils();
				}
			}
		}
		return utils;
	}
	
	/**
	 * @Date: 2018.12.7
	 * @author: wjy329
	 * 功能描述:获取Grid的数据<br/>
	 * @param grid
	 * @return
	 */
	public String getJqgridPageStr(JSONArray grid) {
		JSONObject result = new JSONObject();
		
		PageInfo pageInfo = SystemPageContext.getPageInfo();
		//页面数
		int pageCnt = pageInfo.getPageCnt();
		//总条数
		int recordsCnt = pageInfo.getTotal();
		
		int pageNow = pageInfo.getPageNow();
		
		//设定分页的数据，需要和jqgrid的jsonReader  配置一致
		result.put("rows",grid);
		result.put("page", pageNow);
		result.put("total", pageCnt);
		result.put("records",recordsCnt);
		
		//这个是消息状态码，不是必须的
		result.put("code", ResultCode.OK.getCode());
		result.put("msg",ResultCode.OK.getMsg());

		return result.toJSONString();
	}
	
	public String getLayuiPageStr(JSONArray grid,boolean addId) {
		JSONObject result = new JSONObject();
		
		PageInfo pageInfo = SystemPageContext.getPageInfo();
		//总条数
		int recordsCnt = pageInfo.getTotal();
		int start = pageInfo.getOffset();
		
		if(addId){
			//添加id
			if(grid != null && grid.size() > 0){
				for(int i=0;i<grid.size();i++){
					grid.getJSONObject(i).put("id", ++start);
				}
			}
		}
		
		
		
		//设定分页的数据，需要和jqgrid的jsonReader  配置一致
		result.put("data",grid);
		result.put("count",recordsCnt);
		
		//这个是消息状态码，不是必须的
		result.put("code", ResultCode.OK.getCode());
		result.put("msg",ResultCode.OK.getMsg());

		return result.toJSONString();
		
	}
	/**
	 * @Date: 2018.12.7
	 * @author: wjy329
	 * 功能描述:获取Grid的数据,默认不添加分页<br/>
	 * @param grid 
	 * @return
	 */
	public String getLayuiPageStr(JSONArray grid) {
		return this.getLayuiPageStr(grid,false);
	}
	
	/**
	 * @Date: 2018.12.7
	 * @author: wjy329
	 * 功能描述:写消息到客户端<br/>
	 * @param result
	 * @return
	 */
	public String writeMsg(ResultCode result,String ...info){
		JSONObject json = new JSONObject();
		json.put("code", result.getCode());
		json.put("msg", result.getMsg());
		
		if(info.length >0){
			json.put("info", info[0]);
		}
		return json.toJSONString();
	}
	
	/**
	 * 创建日期:2018年4月26日<br/>
	 * @author: wjy329
	 * 功能描述:<br/>
	 * @param result
	 * @param map 集合
	 * @return
	 */
	public String writeMsg(ResultCode result,Map<String,Object> map){
		JSONObject json = new JSONObject();
		json.put("code", result.getCode());
		json.put("msg", result.getMsg());
		
		if(map!= null){
			for(Map.Entry<String, Object> entity:map.entrySet()){
				json.put(entity.getKey(), entity.getValue());
			}
		}
		return json.toJSONString();
	}
	
	/**
	 * @Date: 2018.12.7
	 * @author: wjy329
	 * 功能描述:写数据到浏览器上<br/>
	 * @param result
	 * @param obj
	 * @return
	 */
	public String writeData(ResultCode result,Object obj){
		JSONObject json = new JSONObject();
		json.put("code", result.getCode());
		json.put("msg", result.getMsg());
		
		json.put("data",JSON.toJSON(obj));
		
		return json.toJSONString();
	}
	
}
