package com.wjy329.wgen.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.dto.SystemPageContext;
import com.wjy329.wgen.dao.WGeneratorDao;
import com.wjy329.wgen.service.WGeneratorService;
import com.wjy329.wgen.utils.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Description TODO
 * @Author wjy329
 * @Date 2019/6/316:55
 **/

@Service
public class WGeneratorServiceImpl implements WGeneratorService {
    @Autowired
    private WGeneratorDao wGeneratorDao;

    @Override
    public JSONArray queryList() {
        //判断条数
        Integer total = 0 ;
        //查询分页数据
        List<Map<String, Object>> result =   null;


        total = this.wGeneratorDao.queryTotal();
        result = this.wGeneratorDao.queryList(SystemPageContext.getPageInfo());


        // 设定总的数据两
        SystemPageContext.setTotal(total);


        JSONArray grid = new JSONArray();
        if(result == null){
            return grid;
        }

        return (JSONArray) JSONArray.parse(JSON.toJSON(result).toString());
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return 0;
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return this.wGeneratorDao.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return this.wGeneratorDao.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for(String tableName : tableNames){
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
