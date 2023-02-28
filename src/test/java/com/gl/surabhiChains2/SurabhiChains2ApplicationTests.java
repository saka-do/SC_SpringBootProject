package com.gl.surabhiChains2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gl.surabhiChains2.entities.Bill;
import com.gl.surabhiChains2.services.SurabhiChainsServiceImpl;
import com.gl.surabhiChains2.services.UserServiceImpl;

@SpringBootTest
class SurabhiChains2ApplicationTests {

	@Autowired
	SurabhiChainsServiceImpl surabhi;
	
	@Autowired
	UserServiceImpl userService;
	@Test
	void contextLoads() {
	}
	
	@Test
	void getUSer()
	{
		System.out.println(userService.getAllUsers());
	}

}
