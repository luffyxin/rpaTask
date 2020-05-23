package com.example.demo.service.impl;

import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Menu;
import com.example.demo.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;
    @Resource
    private RestTemplate template;

    @Override
    public List<Menu> getAllMenu() {
        return menuDao.getAllMenus();
    }
}
