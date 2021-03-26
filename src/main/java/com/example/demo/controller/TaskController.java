package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.base.Verifys;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.biz.TaskBiz;
import com.example.demo.entity.DataFile;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskDataSearchDto;
import com.example.demo.entity.TaskVo;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskBiz taskBiz;

    @Verifys
    @PostMapping("/create")
    public String createTask(@RequestBody Task task){
        Long id = taskBiz.createTask(task);
        return JsonUtil.toJSONL2String(new ApiParam<Long>(id,ApiResult.getOkResult()));
    }

    /**
     * 上传任务数据
     * @param dataFile 数据文件
     * @return 上传结果
     */
    @PostMapping("/upload/data")
    public String uploadTaskData(@RequestBody DataFile dataFile){
        Task task = taskBiz.uploadDataFile(dataFile);
        return JsonUtil.toJSONL2String(new ApiParam<Task>(task,ApiResult.getOkResult()));
    }

    @PostMapping("/upload/data/json")
    public String uploadJsonTaskData(@RequestBody DataFile dataFile){
        Task task = taskBiz.uploadJsonData(dataFile);
        return JsonUtil.toJSONL2String(new ApiParam<Task>(task,ApiResult.getOkResult()));
    }

    @GetMapping("/result/{taskId}")
    public String resultData(@PathVariable("taskId") String taskId){
        taskBiz.exportResultData(Long.parseLong(taskId));
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));
    }

    @GetMapping("/{taskId}")
    public String getTask(@PathVariable("taskId") Long taskId){
        Task task = taskBiz.getTask(taskId);
        return JsonUtil.toJSONL2String(new ApiParam<>(task,ApiResult.getOkResult()));
    }

    @GetMapping("/resource/{taskId}")
    public String resourceData(@PathVariable("taskId") String taskId){
        taskBiz.exportResourceData(Long.parseLong(taskId));
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));

    }


    @DeleteMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId){
        taskBiz.deleteTask(taskId);
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));
    }

    @PutMapping("/update")
    public String updateTask(@RequestBody Task task){
        taskBiz.updateTask(task);
        return JsonUtil.toJSONL2String(new ApiParam<>(ApiResult.getOkResult()));
    }

    @GetMapping("/list/{page}/{limit}")
    public String taskList(@PathVariable("page") Integer page
            ,@PathVariable("limit") Integer limit,Task task){
        ApiParam<TaskVo> taskList = taskBiz.getTaskList(page, limit, task);
        return JsonUtil.toJSONL2String(taskList);
    }

    /**
     * 查看任务数据状态
     * @param taskDataSearchDto 查询条件
     * @return 结果数据
     */
    @PostMapping("/check/data")
    public String checkTaskData(@RequestBody TaskDataSearchDto taskDataSearchDto){
        List<JSONObject> jsonObjects = taskBiz.checkTaskData(taskDataSearchDto);
        return JsonUtil.toJSONL2String(new ApiParam<>(jsonObjects));
    }



}
