package com.wjy329.wgen.service;

import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

public interface WGeneratorService {
	
	JSONArray queryList();
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(String tableName);
	
	List<Map<String, String>> queryColumns(String tableName);
	
	/**
	 * 生成代码
	 */
	byte[] generatorCode(String[] tableNames);
}
