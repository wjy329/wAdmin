package com.wjy329.wshiro.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.constant.Constants;
import com.wjy329.wcommon.constant.UserStatus;
import com.wjy329.wcommon.dto.SystemPageContext;
import com.wjy329.wcommon.utils.PasswordUtil;
import com.wjy329.wshiro.dao.UserDao;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.model.UserInfo;
import com.wjy329.wshiro.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-03 15:17
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findUserByUid(Integer uid) {
        return userDao.getUserByUid(uid);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User regiserUser(User user) {
        //检查用户名
        User dbUser = this.userDao.findByUsername(user.getUsername());
        if(dbUser != null){
            throw new RuntimeException("用户名已存在");
        }

        //通过邮箱来查询用户
        dbUser = this.userDao.getUserByEmail(user.getEmail());
        if(dbUser != null){
            throw new RuntimeException("邮箱已被注册");
        }

        User entity = new User();
        BeanUtils.copyProperties(user, entity);

        entity.setStatus(UserStatus.ACTIVE.getCode());
        String salt = UUID.randomUUID().toString();
        String newPass = PasswordUtil.encryption(entity.getPassword(),salt);
        entity.setPassword(newPass);
        entity.setSalt(salt);
        //插入数据库
        this.userDao.insert(entity);

        //设定id
        user.setUid(entity.getUid());
        return user;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public JSONArray queryByPage(String username) {
        //判断条数
        Integer total = 0 ;
        //查询分页数据
        List<User> result =   null;

        if(!StringUtils.isEmpty(username)){
            username = "%"+username+"%";
            total = this.userDao.getCntByUsername(username);
            result = this.userDao.queryPageByName(SystemPageContext.getPageInfo(),username);
        }else{
            total = this.userDao.getAllCnt();
            result = this.userDao.queryPage(SystemPageContext.getPageInfo());
        }

        // 设定总的数据两
        SystemPageContext.setTotal(total);


        JSONArray grid = new JSONArray();
        if(result == null){
            return grid;
        }

        return (JSONArray) JSONArray.parse(JSON.toJSON(result).toString());
    }

    @Override
    public void deleteByIds(List<String> ids) {
        this.userDao.delUserByIds(ids);
    }

    @Transactional
    @Override
    public void updateStatu(User user) {
        User entity = new User();
        BeanUtils.copyProperties(user, entity);
        this.userDao.updateStatus(entity);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        //获取数据库里面的用户
        User dbUser = this.userDao.getUserByUid(user.getUid());
        //用户名修改的情况
        if(!user.getUsername().equals(dbUser.getUsername())){
            //检查用户名
            User dbUser2= this.userDao.findByUsername(user.getUsername());
            if(dbUser2 != null){
                throw new RuntimeException("用户名已存在");
            }

            //邮箱修改的情况
        }else if(!user.getEmail().equals(dbUser.getEmail())){
            //通过邮箱来查询用户
            User dbUser2 = this.userDao.getUserByEmail(user.getEmail());
            if(dbUser2 != null){
                throw new RuntimeException("邮箱已被注册");
            }
        }
        User entity = new User();
        BeanUtils.copyProperties(user, entity);
        this.userDao.updateUser(entity);
    }

}
