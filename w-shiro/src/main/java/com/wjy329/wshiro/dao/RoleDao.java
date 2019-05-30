package com.wjy329.wshiro.dao;

import com.wjy329.wcommon.dto.PageInfo;
import com.wjy329.wshiro.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author wjy329
 * @Time 2019/1/208:49 AM
 * @description
 */
public interface RoleDao {
    /**
    * @description 根据用户id查找角色信息
    * @author wjy329
    * @Date 2019/1/20
    */
    List<Role> findRoleByUid(Integer uid);
    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:获取所有的角色条数<br/>
     * @return
     */
    Integer getAllRoleCnt();

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:根据用户名来获取到角色的条数<br/>
     * @param name
     * @return
     */
    Integer getRoleCntByName(String name);
    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:读取角色信息<br/>
     * @param rid
     * @return
     */
    Role getRoleById(Integer rid);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:通过角色的id来删除数据<br/>
     * @param rid
     * @return
     */
    int delRoleById(Integer rid);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:插入角色数据<br/>
     * @param entity
     * @return
     */
    int insert(Role entity);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:更新角色<br/>
     * @param entity
     * @return
     */
    int updateRole(Role entity);


    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:根据username来判断<br/>
     * @param page
     * @param name
     * @return
     */
    List<Role>  queryPageByName(@Param(value = "page") PageInfo page, @Param(value = "name") String name);


    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:分页查询用户信息<br/>
     * @param page
     * @return
     */
    List<Role>  queryPage(@Param(value = "page") PageInfo page);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:通过id来批量删除数据<br/>
     * @param ids
     * @return
     */
    Integer delRoleByIds(List<String> ids);


    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:删除角色的按钮表<br/>
     * @param ids
     * @return
     */
    Integer delRoleMenu(List<String> ids);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:删除用户角色表<br/>
     * @param ids
     * @return
     */
    Integer delUserRole(List<String> ids);
    /**
     * 创建日期:2018年5月11日<br/>
     * @author: wjy329
     * 功能描述:通过名称来获取角色<br/>
     * @param name
     * @return
     */
    Role getRoleByName(String name);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:获取所有的角色信息<br/>
     * @return
     */
    List<Role> getAllRoles();

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:<br/>
     * @return
     */
    List<Role> getRolesByUid(Integer uid);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:通过userid来删除用户<br/>
     * @param userId
     * @return
     */
    Integer delRoleByUserId(Integer userId);

    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:插入用户角色信息<br/>
     * @param rid
     * @param uid
     * @return
     */
    Integer insertUserRole(@Param(value = "rid") Integer rid, @Param(value = "uid") Integer uid);


    /**
     * @Date: 2018.12.6
     * @author: wjy329
     * 功能描述:通过用户的id来获取角色的名称信息<br/>
     * @param uid
     * @return
     */
    List<Role> getRolesNameByUid(Integer uid);
}
