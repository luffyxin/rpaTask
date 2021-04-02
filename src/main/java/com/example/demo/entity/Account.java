package com.example.demo.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`t_account`")
public class Account {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`account`")
    private String account;

    @Column(name = "`pwd`")
    private String pwd;

    /**
     * 所属的网站
     */
    @Column(name = "`website`")
    private String website;

    /**
     * 是否可以重复使用
     */
    @Column(name = "`reusable`")
    private Integer reusable;


    /**
     * 领用时间
     */
    @Column(name = "`take_time`")
    private Date takeTime;

    /**
     * 超时时间
     */
    @Column(name = "`limit_hour`")
    private Integer limitHour;

    @Column(name = "`status`")
    private String status;

    /**
     * 手机号
     */
    @Column(name = "`tel`")
    private String tel;

    /**
     * 注册人
     */
    @Column(name = "`owner`")
    private String owner;

    /**
     * 邮箱
     */
    @Column(name = "`mail`")
    private String mail;

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
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 获取所属的网站
     *
     * @return website - 所属的网站
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 设置所属的网站
     *
     * @param website 所属的网站
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 获取是否可以重复使用
     *
     * @return reusable - 是否可以重复使用
     */
    public Integer getReusable() {
        return reusable;
    }

    /**
     * 设置是否可以重复使用
     *
     * @param reusable 是否可以重复使用
     */
    public void setReusable(Integer reusable) {
        this.reusable = reusable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * 获取领用时间
     *
     * @return take_time - 领用时间
     */
    public Date getTakeTime() {
        return takeTime;
    }

    /**
     * 设置领用时间
     *
     * @param takeTime 领用时间
     */
    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    /**
     * 获取超时时间
     *
     * @return limit_hour - 超时时间
     */
    public Integer getLimitHour() {
        return limitHour;
    }

    /**
     * 设置超时时间
     *
     * @param limitHour 超时时间
     */
    public void setLimitHour(Integer limitHour) {
        this.limitHour = limitHour;
    }

    /**
     * 获取手机号
     *
     * @return tel - 手机号
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置手机号
     *
     * @param tel 手机号
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取注册人
     *
     * @return owner - 注册人
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 设置注册人
     *
     * @param owner 注册人
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * 获取邮箱
     *
     * @return mail - 邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置邮箱
     *
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取描述
     *
     * @return describe - 描述
     */
    public String getDescribe() {
        return describe;
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