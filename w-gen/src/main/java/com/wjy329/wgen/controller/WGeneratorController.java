package com.wjy329.wgen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.utils.WebUtils;
import com.wjy329.wgen.service.WGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 代码生成器
 * @Author wjy329
 * @Date 2019/6/3 15:58
 * @Param
 * @return
 **/

@Controller
@RequestMapping("/generator")
public class WGeneratorController {
    @Autowired
    private WGeneratorService wGeneratorService;

    @RequestMapping("/list")
    public String list(){
        return "gen/list";
    }

    @ResponseBody
    @RequestMapping(value="/list",produces="application/json;charset=UTF-8")
    public String listData(){

        //从数据库中获取到数据
        JSONArray rs = this.wGeneratorService.queryList();
        return WebUtils.getInstance().getLayuiPageStr(rs);
    }

    @RequestMapping(value="/code/{tableName}",method= RequestMethod.POST)
    public void code(@PathVariable String tableName, HttpServletResponse response) throws IOException {
        String [] tableNames = tableName.split(",");
        byte[] data = wGeneratorService.generatorCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

}
