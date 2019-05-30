package com.wjy329.wcommon.model;

/**
 * @author 
 */
public class Field{
    /**
     * 主键
     */
    private Long id;

    /**
     * 字段物理名称,比如  name
     */
    private String title;

    /**
     * 字段名称，比如 name
     */
    private String field;

    /**
     * 排序
     */
    private Integer orders;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}