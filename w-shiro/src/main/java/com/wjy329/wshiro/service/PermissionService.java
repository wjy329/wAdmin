package com.wjy329.wshiro.service;

import com.alibaba.fastjson.JSONArray;
import com.wjy329.wshiro.entity.Permission;
import com.wjy329.wshiro.entity.Role;
import com.wjy329.wshiro.entity.RolePerm;
import com.wjy329.wshiro.model.LTree;
import com.wjy329.wshiro.model.Menus;
import com.wjy329.wshiro.model.Tree;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author wjy329
 * @Time 2019/1/139:17 PM
 * @description
 */
public interface PermissionService {
    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:获取左侧的菜单信息<br/>
     * @return
     */
     List<Menus> getMenuNavs();

     List<Tree> getTreeData();

     void updateMenu(Long id,Long newId);

     JSONArray queryByPage(Long pid);

    void update(Permission permission);

    /**
     * @description 根据rid查找权限
     * @author wjy329
     * @Date 2019/1/20
     */
    Set<Permission> getPermByRid(Integer rid);

    /** 
    * @description 获取资源权限
    * @author wjy329
    * @Date 2019/1/20 
    */ 
    Map<String,String> getUrlPerm();


    /**
    * @description 插入菜单/权限信息
    * @author wjy329
    * @Date 2019/1/20
    */
    void insertPerm(Permission permission);

    /**
    * @description 删除菜单/权限
    * @author wjy329
    * @Date 2019/1/20
    */
    void deletePerms(String id);

    /**
    * @description 获取选中的菜单数据
    * @author wjy329
    * @Date 2019/1/21
    */
    List<RolePerm> getSelectedByRid(Integer rid);

    /**
     * 获取选中的菜单数据
     * @param pid
     * @return
     */
    JSONArray queryMenuByPage(Long id);

    /**
    * @description 获取layui的tree的数据
    * @author wjy329
    * @Date 2019-05-31
    */
    List<LTree> getLayTree();
}
