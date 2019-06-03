package com.wjy329.wgen.dao;

import com.wjy329.wcommon.dto.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wjy329
 * @Date 2019/6/316:42
 **/

public interface WGeneratorDao {
    List<Map<String, Object>> queryList(@Param(value = "page") PageInfo page);

    int queryTotal();

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
