package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiResult;
import com.example.demo.entity.ServiceApi;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * @author dx
 */
@Slf4j
@Component
public class RestUtil {

    public static final String HTTP = "http://";

    public static final String[] HTTP_METHODS = {"POST", "GET"};

    @Resource
    private RestTemplate restTemplate;

    public ResponseEntity<String> restQuery(ServiceApi serviceApi) {
        String methodStr = serviceApi.getMethod().toUpperCase();
        // 请求类型错误
        if (!Arrays.asList(HTTP_METHODS).contains(methodStr)) {
            throw new BizException(ApiResult.getErrorResult("30059"));
        }
        // 根据服务名和路径拼接url
        String url = HTTP + serviceApi.getUrl() + serviceApi.getPath();
        JSONObject params = null;
        MultiValueMap<String, String> headers = null;

        // 转换参数
        try {
            params = JSONObject.parseObject(serviceApi.getParams());
            headers = jsonToMap(serviceApi.getHeader());
        } catch (Exception e) {
            e.printStackTrace();
            // json 转换错误
            throw new BizException(ApiResult.getErrorResult("30060"));
        }
        HttpMethod method = HttpMethod.resolve(methodStr);
        return restQuery(url, params, method, headers);
    }

    private MultiValueMap<String, String> jsonToMap(String jsonStr) {
        if (jsonStr == null) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        jsonObject.forEach((key, value) -> result.add(key, value.toString()));
        return result;
    }

    private ResponseEntity<String> restQuery(String url, JSONObject paramsMap,
                                                 HttpMethod method, MultiValueMap<String, String> headerMap) {
        ResponseEntity<String> responseEntity = null;
        // 转换编码格式
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter)
                        httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }

        // 设置头信息
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));

        // 头信息非空的话，添加头信息
        Optional.ofNullable(headerMap).ifPresent(httpHeaders::addAll);

        // 设置头信息和请求参数
        HttpEntity<String> params = new HttpEntity<>(String.valueOf(paramsMap), httpHeaders);

        if (method.equals(HttpMethod.GET)) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            if (paramsMap != null) {
                paramsMap.forEach(builder::queryParam);
            }
            url = builder.build().encode().toString();
        }
        for (int i =0;i< 3;i++){
            try {
                responseEntity = restTemplate.exchange(url, method, params, String.class);
                break;
            } catch (Exception e) {
                // 请求失败
                log.warn("restTemplate  error [message] {}", e.getMessage());
            }
        }
        return responseEntity;
    }
}