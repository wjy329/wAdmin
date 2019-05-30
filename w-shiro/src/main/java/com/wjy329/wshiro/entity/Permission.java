package com.wjy329.wshiro.entity;

/**

 * @description: 权限实体
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-03 14:49
 **/
public class Permission {

    /**
    * 权限id
    * @Author: wjy329
    * @Date: 2018/9/3
    */
    private Long pid;

   /**
    * 权限名称
    * @Author: wjy329
    * @Date: 2018/9/3
    */
    private String title;

    /**
     * 资源url
     * @Author: wjy329
     * @Date: 2018/9/3
     */
    private String href;

    /**
     * 权限标识
     * */
    private String code;

    /**
     * 权限类型  menu   button

     * */
    private Integer type;

    /**
     * 父类id
     */
    private Long parentId;

    private Integer orders;

    private String icon;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
