package com.java.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.java.Exception.DaoException;
import com.java.dao.CustomerDao;
import com.java.domain.Customer;
import com.java.util.DBCPUtil;

public class CustomerDaoImpl implements CustomerDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public void save(Customer c) {
		try {
			qr.update("insert into customers (id,usename,password,phone,address,email,actived,code) values(?,?,?,?,?,?,?,?)", 
					c.getId(),
					c.getUsename(),
					c.getPassword(),
					c.getPhone(),
					c.getAddress(),
					c.getEmail(),
					c.isActived(),
					c.getCode());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Customer findByCode(String code) {
		try {
			return qr.query("select * from customers where code=?", new BeanHandler<Customer>(Customer.class),code);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		
	}

	public void update(Customer c) {
		try {
			qr.update("update customers set usename=?,password=?,phone=?,address=?,email=?,actived=?,code=? where id=?", 
					c.getUsename(),
					c.getPassword(),
					c.getPhone(),
					c.getAddress(),
					c.getEmail(),
					c.isActived(),
					c.getCode(),
					c.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Customer findCustomer(String usename, String password) {
		try {
			return qr.query("select * from customers where usename=? and password=?", new BeanHandler<Customer>(Customer.class),usename,password);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Customer findById(String customerId) {
		try {
			return qr.query("select * from customers where id=?", new BeanHandler<Customer>(Customer.class),customerId);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
