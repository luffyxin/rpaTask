package com.example.demo.entity;

public class RobotDto {

    /**
     * 机器人id
     */
    private Long robotId;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 组id
     */
    private Long groupId;
    /**
     * 流程id
     */
    private Long processId;

    private String processName;
    /**
     * 数据id
     */
    private String dataId;


    public Long getRobotId() {
        return robotId;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
