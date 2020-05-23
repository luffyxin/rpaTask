package com.example.demo.base.Exception;


import com.example.demo.base.response.ApiResult;

/**
 * @Author: dx
 * @Description: 业务异常
 * @Date: 2020/2/7 0007
 * @Version: 1.0
 */
public class BizException extends RuntimeException{

    private ApiResult result;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(ApiResult apiResult) {
        super(apiResult.getErrmsg());
        this.setResult(apiResult);
    }

    public void setResult(ApiResult result) {
        this.result = result;
    }

    public ApiResult getResult() {
        return this.result;
    }
}
