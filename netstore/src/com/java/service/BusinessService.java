package com.java.service;

import java.util.List;

import com.java.commmons.Page;
import com.java.domain.Book;
import com.java.domain.Category;
import com.java.domain.Customer;
import com.java.domain.Order;

public interface BusinessService {

	/**
	 * 添加一个分类
	 * @param category
	 */
	void addCategory(Category category);
	
	/**
	 * 查询所有分类
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * 根据主键查询一个分类
	 * @param categoryId
	 * @return 没有找到返回null
	 */
	Category findCategoryById(String categoryId);
	
	void delCategoryById(String categoryId);
	
	void addBook(Book book);
	
	Page findAllBookPageRecords(String pagenum);
	
	Page findAllBookPageRecords(String pagenum,String categoryId);
	
	Book findBookById(String bookId);
	
	List<Book> findBookByCategoryId(String categoryId);
	
	void delBookById(String bookId);
	
	void regitsCustomer(Customer c);
	
	void activeCustomer(String code);
	
	Customer customerLogin(String usename,String password);
	
	void genOrder(Order order);
	
	void updateOrder(Order order);
	
	Order findOrderById(String orderId);
	
	Order findOrderByOrderNum(String orderNum);
	
	List<Order> findOrdersByCustomer(Customer c);
}

















