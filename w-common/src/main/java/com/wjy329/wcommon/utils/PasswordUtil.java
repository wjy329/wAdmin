package com.wjy329.wcommon.utils;

import java.util.UUID;

/**

 * @description: 加密方式
 *
 * @author: wjy329
 * @create: 2018-12-08 21:19
 **/
public class PasswordUtil {
    public final static String encryption(String password,String saltvalue){
        String pass = MD5Util.md5(MD5Util.md5(password)+saltvalue);
        return pass;
    }

    public static void main(String[] args) {
        String pass = "WJYshr6633153!";
        String salt = UUID.randomUUID().toString();
        String newPass = PasswordUtil.encryption(pass,salt);
        System.out.println("salt"+salt+"==="+"pass"+newPass);
    }
}
