package com.java.dao;

import java.util.List;

import com.java.domain.Order;

public interface OrderDao {

	void save(Order order);

	void update(Order order);

	Order findById(String orderId);

	Order findByOrderNum(String orderNum);

	List<Order> findbyCustomerId(String id);

}
