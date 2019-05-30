package com.wjy329.wshiro.dao;

import com.wjy329.wcommon.dto.PageInfo;
import com.wjy329.wshiro.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-03 15:11
 **/
public interface UserDao {

    /**
    * @Description:  根据用户名查找用户信息
    * @Param:
    * @return:
    * @Author: wjy329
    * @Date: 2018/9/3
    */
    User findByUsername(@Param(value = "username") String username);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:通过邮箱来查询user<br/>
     * @param email
     * @return
     */
    User getUserByEmail(@Param(value = "email") String email);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:插入数据到数据库<br/>
     * @param entity
     * @return
     */
    int insert(User entity);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:分页查询用户信息<br/>
     * @param page
     * @return
     */
    List<User> queryPage(@Param(value = "page") PageInfo page);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:获取总条数<br/>
     * @return
     */
    Integer getAllCnt();

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:根据username 来获取条数<br/>
     * @param username
     * @return
     */
    Integer getCntByUsername(String username);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:根据username来判断<br/>
     * @param page
     * @param username
     * @return
     */
    List<User>  queryPageByName(@Param(value = "page") PageInfo page, @Param(value = "username") String username);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:更新状态信息<br/>
     * @param entity
     * @return
     */
    Integer updateStatus(User entity);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:通过id来获取到用户信息<br/>
     * @param uid
     * @return
     */
    User getUserByUid(Integer uid);


    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:更新用户<br/>
     * @param entity
     * @return
     */
    int updateUser(User entity);

    /**
     * @Date:  2018.12.6
     * @author: wjy329
     * 功能描述:删除多条<br/>
     * @param ids
     * @return
     */
    int delUserByIds(List<String> ids);
}
