package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/** 
 @author Generator 
 @since 2020-02-13T10:38:02.789 
 **/
@Getter
@Setter
public class ServiceApi {
    /**
     * 通过ID生成器自动生成
     */
    @Id
    private Long id;

    /**
     * 编号
     */
    private String number;

    /**
     * 服务名（注册服务名称）
     */
    private String url;

    /**
     * 方法
     */
    private String method;

    /**
     * 路径
     */
    private String path;

    /**
     * 头信息
     */
    private String header;

    /**
     * 请求体
     */
    private String body;

    /**
     * 参数
     */
    private String params;


    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记（0：未删除；1：已删除）
     */
    @Column(name = "delete_flg")
    private String deleteFlg;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新者
     */
    private Long updator;
}