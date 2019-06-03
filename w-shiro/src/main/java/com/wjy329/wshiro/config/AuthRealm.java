package com.wjy329.wshiro.config;


import com.wjy329.wshiro.entity.Permission;
import com.wjy329.wshiro.entity.Role;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.service.PermissionService;
import com.wjy329.wshiro.service.RoleService;
import com.wjy329.wshiro.service.UserService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-03 19:18
 **/
public class AuthRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        //权限列表
        List<String> roleNameList = new ArrayList<>();
        List<String> permissionList = new ArrayList<>();
        Set<Role> roleSet = roleService.getRoleByUid(user.getUid());
        if(CollectionUtils.isNotEmpty(roleSet)){
            for(Role role : roleSet){
                roleNameList.add(role.getRname());
                Set<Permission> permissionSet = permissionService.getPermByRid(role.getRid());
                if(CollectionUtils.isNotEmpty(permissionSet)){
                    for(Permission permission : permissionSet){
                        if(!permission.getCode().isEmpty()){
                            permissionList.add(permission.getCode());
                        }

                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }

    //认证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.findByUsername(username);
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
