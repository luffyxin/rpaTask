package com.example.demo.entity;

import com.example.demo.base.Verify;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "`t_user`")
public class User {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Verify(required = true,name = "用户名",maxLength = 30)
    @Column(name = "`username`")
    private String username;

    @Verify(required = true,name = "密码")
    @Column(name = "`password`")
    private String password;

    public List<String> roles;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 介绍
     */
    @Column(name = "`introduction`")
    private String introduction;

    /**
     * 头像url
     */
    @Column(name = "`avatar`")
    private String avatar;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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
     * 获取介绍
     *
     * @return introduction - 介绍
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置介绍
     *
     * @param introduction 介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取头像url
     *
     * @return avatar - 头像url
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像url
     *
     * @param avatar 头像url
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
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