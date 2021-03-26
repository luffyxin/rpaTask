package com.example.demo.entity;

import com.example.demo.base.Verify;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_robot`")
public class Robot {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 名称
     */
    @Verify(name = "名称",required = true,maxLength = 255)
    @Column(name = "`name`")
    private String name;

    /**
     * 服务器ip
     */
    @Verify(name = "服务器ip",required = true)
    @Column(name = "`server_ip`")
    private String serverIp;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private String status;

    /**
     * 当前在执行的任务id
     */
    @Column(name = "`cur_task_id`")
    private Long curTaskId;

    /**
     * 所在组id
     */
    @Verify(name = "分组",required = true)
    @Column(name = "`group_id`")
    private Long groupId;

    /**
     * 删除标志
     */
    @Column(name = "`delete_flg`")
    private Integer deleteFlg;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取服务器ip
     *
     * @return server_ip - 服务器ip
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * 设置服务器ip
     *
     * @param serverIp 服务器ip
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
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

    /**
     * 获取当前在执行的任务id
     *
     * @return cur_task_id - 当前在执行的任务id
     */
    public Long getCurTaskId() {
        return curTaskId;
    }

    /**
     * 设置当前在执行的任务id
     *
     * @param curTaskId 当前在执行的任务id
     */
    public void setCurTaskId(Long curTaskId) {
        this.curTaskId = curTaskId;
    }

    /**
     * 获取所在组id
     *
     * @return group_id - 所在组id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 设置所在组id
     *
     * @param groupId 所在组id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取删除标志
     *
     * @return delete_flg - 删除标志
     */
    public Integer getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * 设置删除标志
     *
     * @param deleteFlg 删除标志
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
}