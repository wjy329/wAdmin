package com.wjy329.wshiro.service;

import com.alibaba.fastjson.JSONArray;
import com.wjy329.wshiro.entity.Role;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.model.Label;

import java.util.List;
import java.util.Set;

/**
 * @Author wjy329
 * @Time 2019/1/208:55 AM
 * @description
 */

public interface RoleService {

    /**
    * @description 根据uid查找角色
    * @author wjy329
    * @Date 2019/1/20
    */
    Set<Role> getRoleByUid(Integer uid);

    /**
    * @description 根据uid查找用户角色名称
    * @author wjy329
    * @Date 2019/1/20
    */
    List<String> getRnameByUid(Integer uid);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:添加角色<br/>
     * @param role
     */
    public void addRole(Role role);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:分页查询<br/>
     * @return
     */
    public JSONArray queryByPage(String rolename);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:通过id批量删除数据<br/>
     * @param ids
     */
    public void deleteByIds(List<String> ids);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:更新用户<br/>
     * @param role
     */
    public void updateRole(Role role);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:获取角色的label信息<br/>
     * @return
     */
    public List<Label> getRoleLabels(Long uid);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:更新用户的权限信息<br/>
     * @return
     */
    public void updateUserRoles(String roleIds, User user, Long id);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:根据用户的id来获取角色的名称信息<br/>
     * @param uid
     * @return
     */
    public List<String> getRolesNameByUid(Long uid);

    /**
     * @description 根据rid查找role
     * @author wjy329
     * @Date 2019/1/21
     */
    Role findRoleByRid(Integer rid);

}
