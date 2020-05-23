package com.example.demo.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weather" ,url = "localhost:9000")
public interface WeatherClient {


    @GetMapping("/wash/getAll")
    String getWeather();

}
