package com.example.demo.entity;

import com.example.demo.base.Verify;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_process`")
public class Process {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 流程名称
     */
    @Verify(name = "流程名",required = true,maxLength = 255)
    @Column(name = "`name`")
    private String name;

    /**
     * 流程描述
     */
    @Column(name = "`desc`")
    private String desc;

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
     * 获取流程名称
     *
     * @return name - 流程名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置流程名称
     *
     * @param name 流程名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取流程描述
     *
     * @return desc - 流程描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置流程描述
     *
     * @param desc 流程描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
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