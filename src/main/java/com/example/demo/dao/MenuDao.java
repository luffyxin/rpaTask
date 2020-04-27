package com.example.demo.dao;

import com.example.demo.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuDao {

    public List<Menu> getAllMenus();
}
