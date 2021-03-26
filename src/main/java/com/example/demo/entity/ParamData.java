package com.example.demo.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_param_data`")
public class ParamData {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * key
     */
    @Column(name = "`key`")
    private String key;

    /**
     * 值
     */
    @Column(name = "`value`")
    private String value;

    private JSONObject jsonValue;


    /**
     * 描述
     */
    @Column(name = "`describe`")
    private String describe;

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
     * 删除标志
     */
    @Column(name = "`delete_flg`")
    private Integer deleteFlg;

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
     * 获取key
     *
     * @return key - key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置key
     *
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取描述
     *
     * @return describe - 描述
     */
    public String getDescribe() {
        return describe;
    }

    public JSONObject getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(JSONObject jsonValue) {
        this.jsonValue = jsonValue;
    }

    /**
     * 设置描述
     *
     * @param describe 描述
     */
    public void setDescribe(String describe) {
        this.describe = describe;
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
}