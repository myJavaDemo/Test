package com.java.dao;

import com.java.domain.Customer;

public interface CustomerDao {

	void save(Customer c);

	Customer findByCode(String code);

	void update(Customer c);

	Customer findCustomer(String usename, String password);
	
	Customer findById(String customerId);

}
