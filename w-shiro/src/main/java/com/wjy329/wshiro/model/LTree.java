package com.wjy329.wshiro.model;

import java.util.List;

/**
 * @description: layui tree的返回数据
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-05-31 22:33
 **/
public class LTree {

    // 节点名称
    private String label;
    // 节点id
    private Long id;
    // 对应的url
    private String href;
    // 禁用状态
    private Boolean disabled;
    // 禁止拖拽
    private Boolean fixed;
    // 子节点
    private List<LTree> children ;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public List<LTree> getChildren() {
        return children;
    }

    public void setChildren(List<LTree> children) {
        this.children = children;
    }
}
