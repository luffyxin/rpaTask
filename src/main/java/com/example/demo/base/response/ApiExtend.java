package com.example.demo.base.response;

/**
 * @Author: dx
 * @Description: 返回拓展信息
 * @Date: 2020/2/7 0007 14:01
 * @Version: 1.0
 */
public class ApiExtend {
    private Long total;
    private Object meta;

    public ApiExtend() {
    }

    public Object getMeta() {
        return this.meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }



}
