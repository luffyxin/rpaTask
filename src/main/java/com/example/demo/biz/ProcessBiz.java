package com.example.demo.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.demo.base.Exception.BizException;
import com.example.demo.base.response.ApiExtend;
import com.example.demo.base.response.ApiParam;
import com.example.demo.base.response.ApiResult;
import com.example.demo.dao.ProcessMapper;
import com.example.demo.dao.TaskMapper;
import com.example.demo.entity.Process;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProcessBiz {

    @Resource
    private ProcessMapper processMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private RedissonClient redissonClient;

    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    public void createProcess(Process process){
        Long id = snowflake.nextId();
        process.setId(id);
        process.setCreateTime(new Date());
        process.setUpdateTime(new Date());
        processMapper.insertSelective(process);
    }

    public void deleteProcess(Long id){
        Task task = new Task();
        task.setProcessId(id);
        List<TaskVo> taskVos = taskMapper.selectTaskList(task);
        if(taskVos.size() > 0){
            throw new BizException(ApiResult.getErrorResult("30067"));
        }
        processMapper.logicDelete(id);
    }

    public void updateProcess(Process process){
        process.setUpdateTime(new Date());
        processMapper.updateProcess(process);
    }

    public List<Process> getNameList(){
        return processMapper.getNameList();
    }

    public ApiParam<Process> getProcessList(Integer page,Integer limit){
        Page<Object> result = PageHelper.startPage(page, limit);
        List<Process> processList = processMapper.getProcessList();

        ApiExtend apiExtend = new ApiExtend();
        apiExtend.setTotal((long) result.getTotal());

        return new ApiParam<>(processList,apiExtend, ApiResult.getOkResult());
    }

}
