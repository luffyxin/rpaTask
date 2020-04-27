package com.example.demo.controller;

import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dx
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/list")
    public List<Menu> listMenu(){
        return menuService.getAllMenu();
    }

}
