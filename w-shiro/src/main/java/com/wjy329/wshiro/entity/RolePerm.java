package com.wjy329.wshiro.entity;
/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-04 10:15
 **/
public class RolePerm {
    private Long id;

    private Integer rId;

    private Integer pId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
