package com.wjy329.wshiro.model;/**
 * @Author wjy329
 * @Time 2019/1/1410:01 PM
 * @description
 */

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-14 22:01
 **/
public class Tree {
    private Long id;
    private Long pId;
    private String name;
    private Boolean open = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
