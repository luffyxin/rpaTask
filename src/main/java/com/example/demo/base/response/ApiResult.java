package com.example.demo.base.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.util.DateUtil;
import com.example.demo.util.MessageUtil;
import com.example.demo.util.StringUtils;


import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Administrator
 */
public class ApiResult implements Serializable {
    private String errmsg;
    private String errcode;
    private String serverTime;

    public ApiResult() {
    }

    public ApiResult(String errcode, String errmsg, String serverTime) {
        this.errmsg = errmsg;
        this.errcode = errcode;
        this.serverTime = serverTime;
    }

    public static ApiResult getOkResult() {
        return new ApiResult(ResResultCode.SUCCESS.getCode(), "success", DateUtil.getCurrentTime());
    }

    public static ApiResult getErrorResult(ResResultCode resResultCode, String... params) {
        ResourceBundle res = ResourceBundle.getBundle("biz_message");
        String errorMsg = MessageFormat.format(res.getString(resResultCode.getCode()), (Object) params);
        return new ApiResult(resResultCode.getCode(), errorMsg, DateUtil.getCurrentTime());
    }

    public static ApiResult getErrorResult(String messageKey, String... params) {
        return new ApiResult(messageKey, MessageUtil.getText(messageKey, params), DateUtil.getCurrentTime());
    }

    @JSONField(
            deserialize = false,
            serialize = false
    )
    public boolean isSuccessful() {
        return StringUtils.isEquals(this.getErrcode(), ResResultCode.SUCCESS.getCode());
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public String getErrcode() {
        return this.errcode;
    }

    public String getServerTime() {
        return this.serverTime;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiResult)) {
            return false;
        } else {
            ApiResult other = (ApiResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$errmsg = this.getErrmsg();
                    Object other$errmsg = other.getErrmsg();
                    if (this$errmsg == null) {
                        if (other$errmsg == null) {
                            break label47;
                        }
                    } else if (this$errmsg.equals(other$errmsg)) {
                        break label47;
                    }

                    return false;
                }

                Object this$errcode = this.getErrcode();
                Object other$errcode = other.getErrcode();
                if (this$errcode == null) {
                    if (other$errcode != null) {
                        return false;
                    }
                } else if (!this$errcode.equals(other$errcode)) {
                    return false;
                }

                Object this$serverTime = this.getServerTime();
                Object other$serverTime = other.getServerTime();
                if (this$serverTime == null) {
                    if (other$serverTime != null) {
                        return false;
                    }
                } else if (!this$serverTime.equals(other$serverTime)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ApiResult;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $errmsg = this.getErrmsg();
        result = result * 59 + ($errmsg == null ? 43 : $errmsg.hashCode());
        Object $errcode = this.getErrcode();
        result = result * 59 + ($errcode == null ? 43 : $errcode.hashCode());
        Object $serverTime = this.getServerTime();
        result = result * 59 + ($serverTime == null ? 43 : $serverTime.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ApiResult(errmsg=" + this.getErrmsg() + ", errcode=" + this.getErrcode() + ", serverTime=" + this.getServerTime() + ")";
    }
}

