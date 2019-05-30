package com.wjy329.wshiro.entity;

import java.util.HashSet;
import java.util.Set;

/**

 * @description: 角色实体
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-03 14:49
 **/
public class Role {
    /**
     * 角色id
     * @Author: wjy329
     * @Date: 2018/9/3
     */
    private Integer rid;

    /**
     * 角色名称
     * @Author: wjy329
     * @Date: 2018/9/3
     */
    private String rname;

    private String note;


    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
