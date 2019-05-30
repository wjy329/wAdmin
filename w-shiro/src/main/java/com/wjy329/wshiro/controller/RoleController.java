package com.wjy329.wshiro.controller;

import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.constant.Constants;
import com.wjy329.wcommon.constant.ResultCode;
import com.wjy329.wcommon.utils.WebUtils;
import com.wjy329.wshiro.entity.Role;
import com.wjy329.wshiro.service.PermissionService;
import com.wjy329.wshiro.service.RolePermService;
import com.wjy329.wshiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;


/**
 * @Author wjy329
 * @Time 2019/1/207:02 PM
 * @description
 */

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permService;

    @Autowired
    RolePermService rolePermService;

    @RequestMapping("/list")
    public String list(){
        return "role/list";
    }

    @ResponseBody
    @RequestMapping(value="/list",produces="application/json;charset=UTF-8")
    public String listData(String name){

        //从数据库中获取到数据
        JSONArray rs = this.roleService.queryByPage(name);
        return WebUtils.getInstance().getLayuiPageStr(rs);
    }

    /**
     * 角色添加界面<br/>
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "role/add";
    }

    /**
     * 添加角色信息<br/>
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=UTF-8")
    public String addData(Role role){
        try{

            this.roleService.addRole(role);

            return WebUtils.getInstance().writeMsg(ResultCode.OK,"添加成功");
        }catch(Exception e){
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 删除一个或多个角色信息<br/>
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete/{ids}",method= RequestMethod.GET)
    public String delete(@PathVariable String ids){
        try {
            String [] idsArr = ids.split(",");
            for(String rid : Arrays.asList(idsArr)){
                Role role = roleService.findRoleByRid(Integer.valueOf(rid));
                if(Constants.ADMIN_ROLE.equals(role.getRname())){
                    return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "不可能删除管理员角色！");
                }
            }
            this.roleService.deleteByIds(Arrays.asList(idsArr));;
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "删除成功");
        } catch (Exception e) {
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "删除失败");
        }
    }


    /**
     * 更新角色界面<br/>
     * @return
     */
    @RequestMapping("/update")
    public String update(){
        return "role/update";
    }


    @ResponseBody
    @RequestMapping(value="/update",produces="application/json;charset=UTF-8")
    public String updateData(Role role ){
        try {
            this.roleService.updateRole(role);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "更新成功");
        } catch (Exception e) {
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 添加权限的界面<br/>
     * @return
     */
    @RequestMapping("/addPerm")
    public String addPerm2Role(){
        return "role/addPerm";
    }

    @ResponseBody
    @RequestMapping(value="/addMenu",produces="application/json;charset=UTF-8")
    public String add2RoleMenuData(String pids,Integer rId){
        try {
            this.rolePermService.insertMenu2Role(pids,rId);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "权限设定成功");
        } catch (Exception e) {
            e.printStackTrace();
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }
}
