package com.wjy329.wshiro.config;


import com.wjy329.wcommon.utils.PasswordUtil;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 自定义的加密方式
 * */
public class CredentialMatcher extends SimpleCredentialsMatcher {
    @Autowired
    private UserService userService;
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //用户输入的密码
        String password = new String(usernamePasswordToken.getPassword());
        User user = userService.findByUsername(usernamePasswordToken.getUsername());
        String passAddSalt = PasswordUtil.encryption(password,user.getSalt());
        //数据库中保存的密码
        String dbPassword = (String) info.getCredentials();
        return this.equals(passAddSalt,dbPassword);
    }
}
