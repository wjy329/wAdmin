package com.wjy329.wshiro.dao;


import com.wjy329.wcommon.dto.PageInfo;
import com.wjy329.wshiro.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao {
    /**
    * @description  获取所有的权限信息
    * @author wjy329
    * @Date 2019/1/13
    */
    List<Permission> getAllUrls();

    /**
    * @description 查找全部的菜单类型的url
    * @author wjy329
    * @Date 2019/1/22
    */
    List<Permission> getAllMenuUrls();

    /** 
    * @description 根据id查找权限
    * @author wjy329
    * @Date 2019/1/15 
    */ 
    Permission getByPid(Long pid);

    Integer getAllParentCnt();

    List<Permission> queryPageParent(@Param(value = "page") PageInfo page);

    /**
     * @Date:2018.12.6
     * @author wjy329
     * 功能描述:获取子类id<br/>
     * @param pid
     * @return
     */
    Integer getAllChildCnt(@Param(value = "parentId") Long pid);

    /**
     * @Date:2018.12.6
     * @author wjy329
     * 功能描述:根据父类的id来获取子类<br/>
     * @param page
     * @param pid
     * @return
     */
    List<Permission> queryPageChilden(@Param(value = "page") PageInfo page, @Param(value = "parentId") Long pid);

    int update(Permission permission);

    List<Permission> queryPermByUid(Integer uid);

    /**
    * @description 根据uid获取菜单类型的urls
    * @author wjy329
    * @Date 2019/1/22
    */
    List<Permission> getMenuUrlsByUid(Integer uid);

    /**
    * @description 根据角色id查找权限
    * @author wjy329
    * @Date 2019/1/20
    */
    List<Permission> getPermByRid(Integer rid);

    /**
    * @description 查找全部权限
    * @author wjy329
    * @Date 2019/1/20
    */
    List<Permission> getAllPerm();

    /**
    * @description 插入数据到数据库
    * @author wjy329
    * @Date 2019/1/20
    */
    int insert(Permission permission);

    /**
    * @description 删除菜单/权限
    * @author wjy329
    * @Date 2019/1/20
    */
    int deletePerms(String pid);

    /**
    * @description 删除角色菜单信息
    * @author wjy329
    * @Date 2019/1/20
    */
    int deleteRolePerms(String pid);

    List<Permission> queryPageById(@Param(value = "page") PageInfo page, @Param(value = "id") Long id);

    Integer getPageCntById(@Param(value = "id") Long id);

}