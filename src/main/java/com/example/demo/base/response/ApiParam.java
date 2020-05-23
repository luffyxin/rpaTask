package com.example.demo.base.response;



import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ApiParam<T> {
    private List<T> body;
    private ApiExtend extend;
    private ApiResult result;

    public ApiParam() {
    }

    public <T> ApiParam(ApiResult result) {
        this.body = new ArrayList(0);
        this.extend = new ApiExtend();
        this.result = result;
    }

    public <T> ApiParam( List body, ApiExtend extend, ApiResult result) {

        if (body == null) {
            this.body = new ArrayList(0);
        } else {
            this.body = body;
        }

        if (extend == null) {
            this.extend = new ApiExtend();
        } else {
            this.extend = extend;
        }

        this.result = result;
    }

    public ApiParam(T t) {
        if (t == null) {
            this.body = new ArrayList(0);
        } else {
            this.body = new ArrayList(1);
            this.body.add(t);
        }

        this.result = ApiResult.getOkResult();
    }

    public ApiParam(T t, ApiResult result) {
        if (t == null) {
            this.body = new ArrayList(0);
        } else {
            this.body = new ArrayList(1);
            this.body.add(t);
        }

        this.result = result;
    }

    public ApiParam(List body, ApiResult result) {
        if (body == null) {
            this.body = new ArrayList(0);
        } else {
            this.body = body;
        }

        this.result = result;
    }



    public List<T> getBody() {
        return this.body;
    }

    @JSONField(
            serialize = false
    )
    public T getSingleEntity() {
        return this.body != null && !this.body.isEmpty() ? this.body.get(0) : null;
    }

    @JSONField(
            deserialize = false
    )
    public void setSingleEntity(T t) {
        if (this.body != null) {
            this.body.add(t);
        } else {
            this.body = new ArrayList();
            this.body.add(t);
        }

    }

    public void setBody(List<T> body) {
        this.body = body;
    }

    public ApiExtend getExtend() {
        return this.extend;
    }

    public void setExtend(ApiExtend extend) {
        this.extend = extend;
    }

    public ApiResult getResult() {
        return this.result;
    }

    public void setResult(ApiResult result) {
        this.result = result;
    }

    @JSONField(
            deserialize = false,
            serialize = false
    )
    public boolean isSuccessful() {
        return this.result != null && StringUtils.isEquals(this.result.getErrcode(), ResResultCode.SUCCESS.getCode());
    }
}

