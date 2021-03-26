package com.example.demo.entity;

import com.example.demo.base.Verify;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_group`")
public class Group {
    /**
     * 组id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 组名
     */
    @Verify(name = "组名" ,required = true)
    @Column(name = "`name`")
    private String name;

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
     * 获取组id
     *
     * @return id - 组id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置组id
     *
     * @param id 组id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取组名
     *
     * @return name - 组名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置组名
     *
     * @param name 组名
     */
    public void setName(String name) {
        this.name = name;
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