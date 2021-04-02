package com.example.demo.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_account_record`")
public class AccountRecord {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 机器人id
     */
    @Column(name = "`robot_id`")
    private Long robotId;

    /**
     * 账号id
     */
    @Column(name = "`account_id`")
    private Long accountId;

    /**
     * 任务id
     */
    @Column(name = "`task_id`")
    private Long taskId;

    /**
     * 领用时间
     */
    @Column(name = "`use_time`")
    private Date useTime;

    /**
     * 归还时间
     */
    @Column(name = "`back_time`")
    private Date backTime;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private String status;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取机器人id
     *
     * @return robot_id - 机器人id
     */
    public Long getRobotId() {
        return robotId;
    }

    /**
     * 设置机器人id
     *
     * @param robotId 机器人id
     */
    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    /**
     * 获取账号id
     *
     * @return account_id - 账号id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置账号id
     *
     * @param accountId 账号id
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取任务id
     *
     * @return task_id - 任务id
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 设置任务id
     *
     * @param taskId 任务id
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取领用时间
     *
     * @return use_time - 领用时间
     */
    public Date getUseTime() {
        return useTime;
    }

    /**
     * 设置领用时间
     *
     * @param useTime 领用时间
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    /**
     * 获取归还时间
     *
     * @return back_time - 归还时间
     */
    public Date getBackTime() {
        return backTime;
    }

    /**
     * 设置归还时间
     *
     * @param backTime 归还时间
     */
    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }
}