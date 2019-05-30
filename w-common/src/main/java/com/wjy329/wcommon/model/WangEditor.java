package com.wjy329.wcommon.model;/**
 * @Author wjy329
 * @Time 2019/1/2712:54 PM
 * @description
 */

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-27 12:54
 **/

import java.util.Arrays;

public class WangEditor {

    private Integer errno; //错误代码，0 表示没有错误。
    private String[] data; //已上传的图片路径

    public WangEditor() {
        super();
    }
    public WangEditor(String[] data) {
        super();
        this.errno = 0;
        this.data = data;
    }
    public Integer getErrno() {
        return errno;
    }
    public void setErrno(Integer errno) {
        this.errno = errno;
    }
    public String[] getData() {
        return data;
    }
    public void setData(String[] data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "WangEditor [errno=" + errno + ", data=" + Arrays.toString(data)
                + "]";
    }


}

