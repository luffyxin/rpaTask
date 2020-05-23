package com.example.demo.base.Exception;


import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.ResResultCode;

/**
 * @Author: dx
 * @Description: 404异常
 * @Date: 2020/2/7 0007
 * @Version: 1.0
 */
public class NotFoundException extends BizException {

    public NotFoundException(String name){
        this.setResult(ApiResult.getErrorResult(ResResultCode.NOT_FOUND,name));
    }

    public NotFoundException(ApiResult apiResult) {
        super(apiResult);
    }
}
