package com.example.demo;

import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Resource
	private MenuDao menuDao;


	@Test
	void contextLoads() {

	}

}
