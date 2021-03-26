package com.example.demo.entity;

import com.example.demo.base.Verify;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_task`")
public class Task {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 任务名
     */
    @Verify(name = "任务名", required = true)
    @Column(name = "`name`")
    private String name;

    /**
     * 流程id
     */
    @Verify(name = "流程id" ,required = true)
    @Column(name = "`process_id`")
    private Long processId;

    /**
     * 任务状态
     */
    @Column(name = "`status`")
    private String status;

    /**
     * 组id
     */
    @Column(name = "`group_id`")
    private Long groupId;

    /**
     * 数据集合名称
     */
    @Column(name = "`data_name`")
    private String dataName;

    /**
     * 结果数据url
     */
    @Column(name = "`result_url`")
    private String resultUrl;

    /**
     * 源数据url
     */
    @Column(name = "`resource_url`")
    private String resourceUrl;

    /**
     * 优先级
     */
    @Column(name = "`priority`")
    private Integer priority;

    /**
     * 总数据量
     */
    @Column(name = "`data_sum`")
    private Long dataSum;

    /**
     * 已经完成的数量
     */
    @Column(name = "`finish_num`")
    private Long finishNum;

    /**
     * 是否删除
     */
    @Column(name = "`delete_flg`")
    private Integer deleteFlg;

    /**
     * 任务限时
     */
    @Column(name = "`limit_hour`")
    private Integer limitHour;

    /**
     * 开始时间
     */
    @Column(name = "`start_time`")
    private Date startTime;


    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

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
     * 获取任务名
     *
     * @return name - 任务名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置任务名
     *
     * @param name 任务名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取流程id
     *
     * @return process_id - 流程id
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * 设置流程id
     *
     * @param processId 流程id
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * 获取任务状态
     *
     * @return status - 任务状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置任务状态
     *
     * @param status 任务状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取组id
     *
     * @return group_id - 组id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 设置组id
     *
     * @param groupId 组id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取数据集合名称
     *
     * @return data_name - 数据集合名称
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * 设置数据集合名称
     *
     * @param dataName 数据集合名称
     */
    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    /**
     * 获取优先级
     *
     * @return priority - 优先级
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置优先级
     *
     * @param priority 优先级
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getDataSum() {
        return dataSum;
    }

    public void setDataSum(Long dataSum) {
        this.dataSum = dataSum;
    }

    public Long getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Long finishNum) {
        this.finishNum = finishNum;
    }

    /**
     * 获取是否删除
     *
     * @return delete_flg - 是否删除
     */
    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * 设置是否删除
     *
     * @param deleteFlg 是否删除
     */
    public void setDeleteFlg(Integer deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLimitHour() {
        return limitHour;
    }

    public void setLimitHour(Integer limitHour) {
        this.limitHour = limitHour;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}