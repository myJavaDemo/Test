package com.web.struts2;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.ssh.domain.User;
import com.ssh.service.UserService;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	private User user =new User();
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getModel() {
		return user;
	}

	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String save(){
		userService.regist(user);
		return "success";
	}

	
	
}
