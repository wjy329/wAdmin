package com.wjy329.wshiro.config;/**
 * @Author wjy329
 * @Time 2019/1/129:26 PM
 * @description
 */

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-12 21:26
 **/
// AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) { //若当前用户是rolesArray中的任何一个，则有权限访问
                return true;
            }
        }

        return false;
    }
}