package com.example.demo.base.Exception;


import com.example.demo.base.response.ApiResult;

/**
 * @Author: dx
 * @Description: 权限异常
 * @Date: 2020/2/7 0007
 * @Version: 1.0
 */
public class AuthException extends RuntimeException{
    private ApiResult result;

    public AuthException() {
    }

    public AuthException(ApiResult result) {
        this.result = result;
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiResult getResult() {
        return this.result;
    }

    public void setResult(ApiResult result) {
        this.result = result;
    }
}
