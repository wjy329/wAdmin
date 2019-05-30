package com.wjy329.wshiro.model;
/**
 * @Date: 2018.12.7
 * @author: wjy329
 * 功能描述:label对象模型<br/>
 */
public class Label {

    //物理名称
    private String name;
    //值
    private String value;
    //是否选中
    private boolean checked;

    public Label(String value,String name) {
        super();
        this.name = name;
        this.value = value;
    }

    public Label() {
        super();
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
