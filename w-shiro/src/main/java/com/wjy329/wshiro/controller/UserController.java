package com.wjy329.wshiro.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjy329.wcommon.constant.Constants;
import com.wjy329.wcommon.constant.ResultCode;
import com.wjy329.wcommon.utils.WebUtils;
import com.wjy329.wshiro.entity.Role;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.model.Label;
import com.wjy329.wshiro.model.UserInfo;
import com.wjy329.wshiro.service.RoleService;
import com.wjy329.wshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @Author wjy329
 * @Time 2019/1/202:29 PM
 * @description
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/list")
    public String list(){
        return "user/list";
    }

    @ResponseBody
    @RequestMapping(value="/list",produces="application/json;charset=UTF-8")
    public String listData(String username){

        //从数据库中获取到数据
        JSONArray rs = this.userService.queryByPage(username);
        return WebUtils.getInstance().getLayuiPageStr(rs);
    }

    @ResponseBody
    @RequestMapping(value="/update/status",produces="application/json;charset=UTF-8")
    public String updateUserStatu(User user ){
        try {
            this.userService.updateStatu(user);
            return WebUtils.getInstance().writeMsg(ResultCode.OK,"更新成功");
        } catch (Exception e) {
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "更新失败");
        }
    }

    /**
     * 删除一个或多个用户信息<br/>
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete/{ids}",method= RequestMethod.GET)
    public String delete(@PathVariable String ids){
        try {
            String [] idsArr = ids.split(",");
            for(String uid : Arrays.asList(idsArr)){
                User user = userService.findUserByUid(Integer.valueOf(uid));
                if(Constants.ADMIN_ROLE.equals(user.getUsername())){
                    return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "不可能删除管理员的！");
                }
            }
            this.userService.deleteByIds(Arrays.asList(idsArr));
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "删除成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "删除失败");
        }
    }

    /**
     * 更新用户界面<br/>
     * @return
     */
    @RequestMapping("/update")
    public String update(){
        return "user/update";
    }


    @ResponseBody
    @RequestMapping(value="/update",produces="application/json;charset=UTF-8")
    public String updateData(User user ){
        try {
            this.userService.updateUser(user);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "更新成功");
        } catch (Exception e) {
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 给用户添加角色信息<br/>
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole2User(){
        return "user/addRole";
    }


    /**
     * 获取角色信息<br/>
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/role/{uid}")
    public String getRoleInfo(@PathVariable Long uid){
        List<Label> labels = roleService.getRoleLabels(uid);
        return WebUtils.getInstance().writeData(ResultCode.OK, labels);
    }

    /**
     * 添加角色信息<br/>
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/addRole",produces="application/json;charset=UTF-8")
    public String add2RoleUserData(String roleIds,Long userId){
        try {
            //设定到session中
            User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

            //获取role的信息
            this.roleService.updateUserRoles(roleIds, user,userId);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "角色更新成功");
        } catch (Exception e) {
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }

    /**
     * 返回个人中心页面
     * @author wjy329
     * @return
     */
    @RequestMapping("/myCenter")
    public String myCenter(){
        return "user/myCenter";
    }

    @ResponseBody
    @RequestMapping(value="/myCenter",produces="application/json;charset=UTF-8")
    public String myData(){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        StringBuilder rnameStr = new StringBuilder();
        // 获取角色名称
        List<String> roleNames = roleService.getRnameByUid(user.getUid());

        for(String roleName : roleNames){
            rnameStr.append(roleName);
            rnameStr.append(",");
        }
        //去掉最后的一个 ,
        String rolesStr = rnameStr.substring(0, rnameStr.length()-1);
        UserInfo userInfo = new UserInfo();
        userInfo.setCname(user.getCname());
        userInfo.setEmail(user.getEmail());
        userInfo.setRoleName(rolesStr);
        JSONObject res = (JSONObject) JSONObject.toJSON(userInfo);
        return res.toJSONString();
    }

}
