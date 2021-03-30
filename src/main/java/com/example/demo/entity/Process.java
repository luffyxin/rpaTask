package com.example.demo.entity;

import com.example.demo.base.Verify;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Setter
@Getter
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
     * 流程名称
     */
    @Verify(name = "单条数据执行时间限制",required = true)
    @Column(name = "`data_time_limit`")
    private String dataTimeLimit;

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


}