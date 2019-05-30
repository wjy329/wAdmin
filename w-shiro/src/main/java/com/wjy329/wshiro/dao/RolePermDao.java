package com.wjy329.wshiro.dao;


import com.wjy329.wshiro.entity.RolePerm;

import java.util.List;


/**
 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-04 10:18
 **/
public interface RolePermDao{
    /**
     * @description 插入数据到数据库
     * @author wjy329
     * @Date 2019/1/20
     */
    int insert(RolePerm rolePerm);

    /**
    * @description 根据角色rid查找菜单
    * @author wjy329
    * @Date 2019/1/21
    */
    List<RolePerm> getRolePermByRid(Integer rid);

    /**
    * @description 根据rid删除权限
    * @author wjy329
    * @Date 2019/1/21
    */
    void deleteRolePermByRid(Integer rId);
}
