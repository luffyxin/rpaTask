package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.entity.Menu;
import com.example.demo.entity.ServiceApi;
import com.example.demo.feign.WeatherClient;
import com.example.demo.service.MenuService;

import com.example.demo.util.RestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dx
 */
@RestController
@RequestMapping("test")
public class MenuController {

    @Resource
    private MenuService menuService;

    @Resource
    private WeatherClient weatherClient;

    @Resource
    private RestUtil restUtil;

    @GetMapping("/rest")
    public Object hello(){

        ServiceApi api = new ServiceApi();
        api.setUrl("localhost:9000");
        api.setPath("/wash/getAll");
        api.setMethod("GET");
        ResponseEntity<String> jsonObjectResponseEntity = restUtil.restQuery(api);

        return jsonObjectResponseEntity;
    }

    @GetMapping("/feign")
    public ApiParam<String> hellofeign(String error){
        String weather = weatherClient.getWeather();
        return new ApiParam<>(weather, ApiResult.getOkResult());
    }

    @GetMapping("/list")
    public List<Menu> listMenu(){
        return menuService.getAllMenu();
    }

}
