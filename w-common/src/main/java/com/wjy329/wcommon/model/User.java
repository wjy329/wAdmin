package com.wjy329.wcommon.model;

import java.util.Date;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-12-06 22:15
 **/
public class User {
    /**
     * 用户id
     * @Author: wjy329
     * @Date: 2018/9/3
     */
    private Integer uid;

    /**
     * 用户名称
     * @Author: wjy329
     * @Date: 2018/9/3
     */
    private String username;

    /**
     * 用户密码
     * @Author: wjy329
     * @Date: 2018/9/3
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 1:有效，0:禁止登录
     */
    private Integer status;

    /**
     * 中文名
     */
    private String cname;

    /**
     * 盐值
     * */
    private String salt;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
