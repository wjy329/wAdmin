package com.wjy329.wshiro.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wjy329.wcommon.constant.Constants;
import com.wjy329.wcommon.dto.SystemPageContext;
import com.wjy329.wshiro.dao.RoleDao;
import com.wjy329.wshiro.entity.Role;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.model.Label;
import com.wjy329.wshiro.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-20 08:56
 **/
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<Role> getRoleByUid(Integer uid) {
        Set<Role> result = new HashSet<>();
        List<Role> roles = roleDao.findRoleByUid(uid);
        for(Role role : roles){
            result.add(role);
        }
        return result;
    }

    @Override
    public List<String> getRnameByUid(Integer uid) {
        List<Role> roles = roleDao.findRoleByUid(uid);
        List<String> result = new ArrayList<>();
        for(Role role : roles){
            result.add(role.getRname());
        }
        return result;
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        if(role.getRname().trim().equalsIgnoreCase(Constants.ADMIN_ROLE)){
            throw new RuntimeException("admin角色为系统角色<br/>不可添加");
        }
        Integer cnt = this.roleDao.getRoleCntByName(role.getRname());
        if(cnt >0){
            throw new RuntimeException("角色已经存在");
        }

        //添加角色信息
        Role entity = new Role();
        BeanUtils.copyProperties(role, entity);
        this.roleDao.insert(entity);
    }

    @Override
    public JSONArray queryByPage(String rname) {
        // 判断条数
        Integer total = 0;
        // 查询分页数据
        List<Role> result = null;

        if (!StringUtils.isEmpty(rname)) {
            rname = "%" + rname + "%";
            total = this.roleDao.getRoleCntByName(rname);
            result = this.roleDao.queryPageByName(SystemPageContext.getPageInfo(), rname);
        } else {
            total = this.roleDao.getAllRoleCnt();
            result = this.roleDao.queryPage(SystemPageContext.getPageInfo());
        }

        // 设定总的数据两
        SystemPageContext.setTotal(total);

        JSONArray grid = new JSONArray();
        if (result == null) {
            return grid;
        }

        return (JSONArray) JSONArray.parse(JSON.toJSON(result).toString());
    }


    @Transactional
    @Override
    public void deleteByIds(List<String> ids) {
        this.roleDao.delRoleByIds(ids);

        //删除角色的菜单关联表
        this.roleDao.delRoleMenu(ids);

        //删除角色关联表
        this.roleDao.delUserRole(ids);
    }

    @Transactional
    @Override
    public void updateRole(Role role) {

        //解决重复的角色
        Role dbEntity = this.roleDao.getRoleByName(role.getRname());
        if(dbEntity != null && !dbEntity.getRid().equals(role.getRid())){
            throw new RuntimeException("角色已经存在");
        }

        Role entity = new Role();
        BeanUtils.copyProperties(role, entity);
        this.roleDao.updateRole(entity);
    }

    @Override
    public List<Label> getRoleLabels(Long uid) {
        List<Role> roles = this.roleDao.getAllRoles();
        if(roles == null || roles.size() == 0){
            return null;
        }

        List<Label> labels = new ArrayList<Label>();
        for(Role role :roles){
            Label label  = new Label(String.valueOf(role.getRid()),role.getRname());
            labels.add(label);
        }

        List<Role> selLabels = this.roleDao.getRolesByUid(uid.intValue());
        if(selLabels == null || selLabels.size() ==0){
            return labels;
        }

        for(Label webLabel:labels){
            for(Role sel:selLabels){
                if(webLabel.getValue().equals(Long.toString(sel.getRid()))){
                    webLabel.setChecked(true);
                }
            }
        }

        return labels;
    }


    @Override
    public void updateUserRoles(String roleIds, User user, Long uid) {

        //先删除用户拥有的角色
        this.roleDao.delRoleByUserId(uid.intValue());

        //更新用户的角色信息
        if(StringUtils.isEmpty(roleIds)){
            return;
        }
        String [] rids = roleIds.split(",");
        for(String rid:rids){
            this.roleDao.insertUserRole(Integer.valueOf(rid), uid.intValue());
        }
    }

    @Override
    public List<String> getRolesNameByUid(Long uid) {
        List<Role> rs = this.roleDao.getRolesNameByUid(uid.intValue());
        if(rs == null || rs.size() ==0){
            return null;
        }

        //设定用户的角色信息
        List<String> names = new ArrayList<String>(rs.size());
        for(Role role:rs){
            names.add(role.getRname());
        }

        return names;
    }

    @Override
    public Role findRoleByRid(Integer rid) {
        return roleDao.getRoleById(rid);
    }
}
