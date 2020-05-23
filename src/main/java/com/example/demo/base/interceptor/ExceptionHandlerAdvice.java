package com.example.demo.base.interceptor;

import com.alibaba.fastjson.JSONException;
import com.example.demo.base.Exception.AuthException;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.Exception.NotFoundException;
import com.example.demo.base.Exception.VerifyException;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.ResResultCode;
import com.example.demo.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 全局异常拦截
 * @author Administrator
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {

    /**
     * 通用异常拦截
     * 捕获未知异常
     * 返回 500
     *
     * @param e Exception
     * @return ApiParam
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        e.printStackTrace();
        ApiResult result = ApiResult.getErrorResult(ResResultCode.SYSTEM_ERR);
        ApiParam requestParam = new ApiParam(result);
        return JsonUtil.toJSON(requestParam);
    }

    /**
     * json转换失败异常拦截
     * 返回 500
     *
     * @param e Exception
     * @return ApiParam
     */
    @ExceptionHandler(JSONException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleJsonException(JSONException e) {
        ApiResult result = ApiResult.getErrorResult(ResResultCode.DESERIALIZE_ERR, e.getMessage());
        ApiParam requestParam = new ApiParam(result);
        return JsonUtil.toJSON(requestParam);
    }

    /**
     * 通用业务异常拦截
     * 捕获主动抛出的业务异常
     * 返回 400
     *
     * @param e Exception
     * @return ApiParam
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBizException(BizException e) {
        ApiResult result = e.getResult();
        if (result == null) {
            result = ApiResult.getErrorResult(ResResultCode.SYSTEM_ERR, e.getMessage());
        }
        ApiParam requestParam = new ApiParam(result);
        return JsonUtil.toJSON(requestParam);
    }

    /**
     * 权限异常拦截
     * 权限认证失败异常
     * 返回 401
     *
     * @param e Exception
     * @return ApiParam
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthException(AuthException e) {
        ApiResult result = e.getResult();
        if (e.getResult() == null) {
            result = ApiResult.getErrorResult(ResResultCode.INVALID_CLIENT);
        }
        ApiParam requestParam = new ApiParam(result);
        return JsonUtil.toJSON(requestParam);
    }

    /**
     * 未找到对应资源异常拦截
     * 未找到相关资源
     * 返回 404
     *
     * @param e Exception
     * @return ApiParam
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        ApiResult result = e.getResult();
        if (result == null) {
            result = ApiResult.getErrorResult(ResResultCode.SYSTEM_ERR, e.getMessage());
        }
        ApiParam requestParam = new ApiParam(result);
        return JsonUtil.toJSON(requestParam);
    }

    /**
     * 实体解析失败异常拦截
     * 接口参数验证失败异常
     * 返回 422
     *
     * @param e Exception
     * @return ApiParam
     */
    @ExceptionHandler(VerifyException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleVerifyException(VerifyException e) {
        ApiResult result = e.getResult();
        if (result == null) {
            result = ApiResult.getErrorResult(ResResultCode.SYSTEM_ERR, e.getMessage());
        }
        ApiParam requestParam = new ApiParam(result);
        return JsonUtil.toJSON(requestParam);
    }

}
