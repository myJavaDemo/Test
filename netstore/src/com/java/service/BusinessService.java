package com.java.service;

import java.util.List;

import com.java.commmons.Page;
import com.java.domain.Book;
import com.java.domain.Category;
import com.java.domain.Customer;
import com.java.domain.Order;

public interface BusinessService {

	/**
	 * ���һ������
	 * @param category
	 */
	void addCategory(Category category);
	
	/**
	 * ��ѯ���з���
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * ����������ѯһ������
	 * @param categoryId
	 * @return û���ҵ�����null
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

















