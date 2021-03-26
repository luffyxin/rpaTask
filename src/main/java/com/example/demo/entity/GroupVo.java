package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupVo extends Group{

    public List<Robot> robotList;

}
