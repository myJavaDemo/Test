package com.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssh.domain.User;
import com.ssh.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TestData {
	@Autowired
	private UserService userService;
	
	@Test
	public void TestDemo(){
		User  user = new User();
		user.setName("MM");
		user.setPassword("66666");
		userService.regist(user);
		
	}
}
