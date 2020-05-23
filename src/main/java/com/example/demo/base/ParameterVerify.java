package com.example.demo.base;


import com.example.demo.base.Exception.VerifyException;
import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.ResResultCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
@Component
@Aspect
public class ParameterVerify {

    @Before(value="@annotation(com.example.demo.base.Verifys)")
    public void parameterVerify(JoinPoint point) throws IllegalAccessException {
        //切点对象
        Object obj = point.getArgs()[0];
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            //需要做校验的参数
            if(field.isAnnotationPresent(Verify.class)){
                Verify verify = field.getAnnotation(Verify.class);
                String name = verify.name();
                int maxLength = verify.maxLength();
                int minLength = verify.minLength();
                boolean required = verify.required();
                String regular = verify.regular();
                //属性值
                Object fieldObj = field.get(obj);
                if(required){
                    if(fieldObj == null || "".equals(fieldObj)){
                        throw new VerifyException(ApiResult.getErrorResult(ResResultCode.REQUIRED_ERR,name));
                    }
                }
                if(Integer.MAX_VALUE != maxLength ){
                    if(maxLength < String.valueOf(fieldObj).length()){
                        throw new VerifyException(ApiResult.getErrorResult(ResResultCode.INVALID_MAX_LENGTH,name,Integer.toString(maxLength)));
                    }
                }
                if(Integer.MIN_VALUE != minLength){
                    if(minLength > String.valueOf(fieldObj).length()){
                        throw new VerifyException(ApiResult.getErrorResult(ResResultCode.INVALID_MIN_LENGTH,name,Integer.toString(minLength)));

                    }
                }
                if(!"".equals(regular)){
                    Pattern pattern = Pattern.compile(regular);
                    if (!pattern.matcher(String.valueOf(fieldObj)).matches()) {
                        throw new VerifyException(ApiResult.getErrorResult(ResResultCode.INVALID_PARAM,name));

                    }
                }
            }
        }
    }
}
