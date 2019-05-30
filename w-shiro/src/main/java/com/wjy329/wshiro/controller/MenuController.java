package com.wjy329.wshiro.controller;/**
 * @Author wjy329
 * @Time 2019/1/1310:39 PM
 * @description
 */

import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.constant.ResultCode;
import com.wjy329.wcommon.utils.WebUtils;
import com.wjy329.wshiro.entity.Permission;
import com.wjy329.wshiro.entity.RolePerm;
import com.wjy329.wshiro.model.Menus;
import com.wjy329.wshiro.model.Tree;
import com.wjy329.wshiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-13 22:39
 **/
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private PermissionService permissionService;
    @ResponseBody
    @RequestMapping(value="/navs",produces="application/json;charset=UTF-8")
    public String tregetMenuNavse(){
        List<Menus> menus = this.permissionService.getMenuNavs();
        return WebUtils.getInstance().writeData(ResultCode.OK,menus);
    }


    @RequestMapping("/list")
    public String list(){
        return "menu/list";
    }

    @ResponseBody
    @RequestMapping(value="/treeData",produces="application/json;charset=UTF-8")
    public String treeData(){
        List<Tree> trees = permissionService.getTreeData();
        return WebUtils.getInstance().writeData(ResultCode.OK,trees);
    }

    @ResponseBody
    @RequestMapping(value="/tableList",produces="application/json;charset=UTF-8")
    public String listData(Long pid){
        //从数据库中获取到数据
        JSONArray rs = this.permissionService.queryByPage(pid);
        return WebUtils.getInstance().getLayuiPageStr(rs);
    }

    @ResponseBody
    @RequestMapping(value="/getTableData",produces="application/json;charset=UTF-8")
    public String getTableData(Long id){
        //从数据库中获取到数据
        JSONArray rs = this.permissionService.queryMenuByPage(id);
        return WebUtils.getInstance().getLayuiPageStr(rs);
    }

    @RequestMapping("/update")
    public String update(){
        return "menu/update";
    }

    @ResponseBody
    @RequestMapping(value="/update",produces="application/json;charset=UTF-8")
    public String updateData(Permission permission){
        try {
            this.permissionService.update(permission);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "更新成功");
        } catch (Exception e) {
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "更新失败");
        }
    }

    @RequestMapping("/add")
    public String add(){
        return "menu/add";
    }
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=UTF-8")
    public String addData(Permission permission){
        try{
            this.permissionService.insertPerm(permission);
            return WebUtils.getInstance().writeMsg(ResultCode.OK,"菜单添加成功");
        }catch(Exception e){
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value="/delete/{id}",method= RequestMethod.GET)
    public String delete(@PathVariable String id){
        try {
            this.permissionService.deletePerms(id);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "删除成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value="/sel/{rid}",method=RequestMethod.GET)
    public String getSelMenuIdByRoleId(@PathVariable Integer rid){
        List<RolePerm> rolePerms = permissionService.getSelectedByRid(rid);
        return WebUtils.getInstance().writeData(ResultCode.OK,rolePerms);
    }

}
