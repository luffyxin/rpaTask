package com.example.demo.controller;

import com.example.demo.base.Verifys;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.biz.GroupBiz;
import com.example.demo.entity.Group;
import com.example.demo.entity.GroupVo;
import com.example.demo.entity.Process;
import com.example.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupBiz groupBiz;

    @Verifys
    @PostMapping("/create")
    public String createGroup(@RequestBody Group group) {
        groupBiz.createGroup(group);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @PutMapping("/change/robot")
    public String changeRobotGroup(@RequestBody List<GroupVo> groupVoList){
        groupBiz.changeRobotGroup(groupVoList);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @PutMapping("/update")
    public String updateGroup(@RequestBody Group group){
        groupBiz.updateGroup(group);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGroup(@PathVariable("id")Long id){
        groupBiz.deleteGroup(id);
        return JsonUtil.toJSONL2String(new ApiParam<String>(ApiResult.getOkResult()));
    }

    @GetMapping("/name/list")
    public String getNameList() {
        List<Group> nameList = groupBiz.getNameList();
        return JsonUtil.toJSONL2String(new ApiParam<Process>(nameList, ApiResult.getOkResult()));
    }

    @GetMapping("/list/{page}/{limit}")
    public String getGroupList(@PathVariable("page") Integer page
            ,@PathVariable("limit") Integer limit) {

        ApiParam<GroupVo> groupList = groupBiz.getGroupList(page, limit);
        return JsonUtil.toJSONL2String(groupList);
    }

}
