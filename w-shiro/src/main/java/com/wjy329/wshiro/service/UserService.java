package com.wjy329.wshiro.service;


import com.alibaba.fastjson.JSONArray;
import com.wjy329.wshiro.entity.User;

import java.util.List;

/**
 * @Author: wjy329
 * @Time: 2018/9/3下午3:16
 */
public interface UserService {

    /**
    * @description 根据uid查找user
    * @author wjy329
    * @Date 2019/1/20
    */
    User findUserByUid(Integer uid);

    User findByUsername(String username);


    /**
     * @description 注册用户
     * @param  user
     * @author wjy329
     * @Date 2018/12/8
     */

    User regiserUser(User user);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:添加用户<br/>
     * @param user
     * @return
     */
     User addUser(User user);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:分页查询<br/>
     * @return
     */
     JSONArray queryByPage(String username);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:通过id批量删除数据<br/>
     * @param ids
     */
     void deleteByIds(List<String> ids);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:更新用户的状态,禁用还是启用<br/>
     * @param user
     */
     void updateStatu(User user);

    /**
     * @Date: 2018.12.7
     * @author: wjy329
     * 功能描述:更新用户<br/>
     * @param user
     */
     void updateUser(User user);
}
