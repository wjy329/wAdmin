package com.wjy329.wshiro.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.wjy329.wshiro.dao.RolePermDao;
import com.wjy329.wshiro.entity.RolePerm;
import com.wjy329.wshiro.service.RolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-04 10:12
 **/
@Service
public class RolePermServiceImpl implements RolePermService {
    @Autowired
    RolePermDao rolePermDao;


    @Override
    public void insertMenu2Role(String pids, Integer rId) {
        //删除所有的角色信息
        this.rolePermDao.deleteRolePermByRid(rId);

        if(StringUtils.isEmpty(pids)){
            return;
        }
        //插入角色信息
        String [] midArr = pids.split(",");
        for(String mid:midArr){
            RolePerm rolePerm = new RolePerm();
            rolePerm.setrId(rId);
            rolePerm.setpId(Integer.valueOf(mid));
            this.rolePermDao.insert(rolePerm);
        }
    }
}

