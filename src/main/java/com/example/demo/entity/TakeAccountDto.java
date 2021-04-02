package com.example.demo.entity;


import com.example.demo.base.Verify;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class TakeAccountDto {

    private Long recordId;

    private Long taskId;

    private Long robotId;

    private Long accountId;

    private String website;

    private String account;

    private String pwd;

}
