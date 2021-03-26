package com.example.demo.controller;



import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Verifys;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.biz.RobotBiz;
import com.example.demo.entity.Robot;
import com.example.demo.entity.RobotDto;
import com.example.demo.entity.RobotVo;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/robot")
public class RobotController {

    @Resource
    private RobotBiz robotBiz;

    @Verifys
    @PostMapping("/create")
    public String createRobot(@RequestBody Robot robot) {
        robotBiz.createRobot(robot);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @Verifys
    @PutMapping("/update")
    public String updateRobot(@RequestBody Robot robot){
        robotBiz.updateRobot(robot);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @GetMapping("/list/{page}/{limit}")
    public String getRobotList(@PathVariable("page") Integer page
            , @PathVariable("limit") Integer limit
            , Robot robot) {
        ApiParam<RobotVo> result = robotBiz.getRobotList(page, limit, robot);
        return JsonUtil.toJSONL2String(result);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRobot(@PathVariable("id") Long id) {
        robotBiz.deleteRobot(id);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @PostMapping("/start/task")
    public String startTask(@RequestBody RobotDto robotDto) {
        RobotDto dto = robotBiz.robotStartTask(robotDto);
        return JsonUtil.toJSONL2String(dto);
    }

    @PostMapping("/data/finish")
    public String dataFinish(@RequestBody JSONObject jsonObject) {
        robotBiz.finishOneData(jsonObject);
        return JsonUtil.toJSONL2String(ApiResult.getOkResult());
    }

    @PostMapping("/task/data")
    public String getTaskData(@RequestBody RobotDto robotDto) {
        return robotBiz.getTaskData(robotDto);
    }

}
