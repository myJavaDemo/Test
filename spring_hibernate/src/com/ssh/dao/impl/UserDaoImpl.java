package com.ssh.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ssh.dao.UserDao;
import com.ssh.domain.User;

public class UserDaoImpl implements UserDao {
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public void save(User user) {
		this.hibernateTemplate.save(user);
	}

}
