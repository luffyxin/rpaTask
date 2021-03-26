package com.example.demo.biz;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiExtend;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.dao.GroupMapper;
import com.example.demo.dao.RobotMapper;
import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class GroupBiz {


    @Resource
    private GroupMapper groupMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RobotMapper robotMapper;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);


    /**
     * 删除分组
     *
     * @param id 组id
     */
    public void deleteGroup(Long id) {

        // 判断组内是否关联有任务
        Task task = new Task();
        task.setGroupId(id);
        List<TaskVo> taskVos = taskMapper.selectTaskList(task);
        if (taskVos.size() > 0) {
            throw new BizException(ApiResult.getErrorResult("30063"));
        }
        // 判断是否关联有机器人
        Robot robot = new Robot();
        robot.setGroupId(id);
        List<RobotVo> robotVos = robotMapper.selectRobotList(robot);
        if (robotVos.size() > 0) {
            throw new BizException(ApiResult.getErrorResult("30064"));
        }
        groupMapper.logicDelete(id);
    }

    public void updateGroup(Group group){
        group.setUpdateTime(new Date());
        groupMapper.updateGroup(group);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeRobotGroup(List<GroupVo> groupVoList) {

        for (GroupVo groupVo : groupVoList) {
            // 清空分组下的机器人
            robotMapper.clearGroupRobot(groupVo);
            if (groupVo.getRobotList().size() > 0) {
                // 重新分配机器人
                robotMapper.updateRobotGroup(groupVo);
            }
        }

    }

    public void createGroup(Group group) {

        int i = groupMapper.selectCountByName(group.getName());

        if (i > 0) {
            throw new BizException(ApiResult.getErrorResult("30062"));
        } else {
            Long id = snowflake.nextId();
            group.setId(id);
            group.setCreateTime(new Date());
            group.setUpdateTime(new Date());
            groupMapper.insertSelective(group);
        }
    }

    public ApiParam<GroupVo> getGroupList(Integer pageNum, Integer pageSize) {
        // 每个分组机器人的数量
        List<Integer> list = groupMapper.getGroupListGroupById();
        int skipNumber = list.subList(0, Math.min((pageNum - 1) * pageSize, list.size())).stream().mapToInt(Integer::intValue).sum();
        int interPageSize = list.subList((pageNum - 1) * pageSize, Math.min(pageNum * pageSize, list.size())).stream().mapToInt(Integer::intValue).sum();
        List<GroupVo> groupList = groupMapper.getGroupList(skipNumber, interPageSize);
        ApiExtend apiExtend = new ApiExtend();
        apiExtend.setTotal((long) list.size());
        return new ApiParam<>(groupList, apiExtend, ApiResult.getOkResult());
    }

    public List<Group> getNameList() {
        return groupMapper.findNameList();
    }

}
