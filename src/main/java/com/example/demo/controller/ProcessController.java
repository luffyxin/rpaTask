package com.example.demo.controller;

import com.example.demo.base.Verifys;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.biz.ProcessBiz;
import com.example.demo.entity.Process;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Resource
    private ProcessBiz processBiz;

    @PostMapping("/create")
    public String createProcess(@RequestBody Process process) {
        processBiz.createProcess(process);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @GetMapping("/list/{page}/{limit}")
    public String getProcessList(@PathVariable("page")Integer page
            ,@PathVariable("limit")Integer limit){
        ApiParam<Process> result = processBiz.getProcessList(page, limit);
        return JsonUtil.toJSONL2String(result);
    }


    @Verifys
    @PutMapping("/update")
    public String updateProcess(@RequestBody Process process){
        processBiz.updateProcess(process);
        return JsonUtil.toJSONL2String(new ApiParam<Process>(ApiResult.getOkResult()));
    }

    @GetMapping("/name/list")
    public String getNameList() {
        List<Process> nameList = processBiz.getNameList();
        return JsonUtil.toJSONL2String(new ApiParam<Process>(nameList, ApiResult.getOkResult()));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProcess(@PathVariable("id")Long id){
        processBiz.deleteProcess(id);
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));
    }

}
