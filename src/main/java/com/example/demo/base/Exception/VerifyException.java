package com.example.demo.base.Exception;

import com.example.demo.base.response.ApiResult;

/**
 * @Author: dx
 * @Description: 参数验证异常
 * @Date: 2020/2/7 0007
 * @Version: 1.0
 */
public class VerifyException extends BizException {

    public VerifyException(ApiResult apiResult) {
        super(apiResult);
    }

}
