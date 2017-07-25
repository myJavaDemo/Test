package com.java.service.impl;

import java.util.List;
import java.util.UUID;

import com.java.commmons.Page;
import com.java.dao.BookDao;
import com.java.dao.CategoryDao;
import com.java.dao.CustomerDao;
import com.java.dao.OrderDao;
import com.java.dao.impl.BookDaoImpl;
import com.java.dao.impl.CategoryDaoImpl;
import com.java.dao.impl.CustomerDaoImpl;
import com.java.dao.impl.OrderDaoImpl;
import com.java.domain.Book;
import com.java.domain.Category;
import com.java.domain.Customer;
import com.java.domain.Order;
import com.java.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
	private CategoryDao categoryDao = new CategoryDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	private CustomerDao customerDao = new CustomerDaoImpl();
	private OrderDao orderDao = new OrderDaoImpl();
	
	public void addCategory(Category category) {
		category.setId(UUID.randomUUID().toString());
		categoryDao.save(category);
	}

	public List<Category> findAllCategories() {
		return categoryDao.findAll();
	}

	public Category findCategoryById(String categoryId) {
		return categoryDao.findById(categoryId);
	}
	
	public void delCategoryById(String categoryId) {
		categoryDao.delById(categoryId);
	}

	public void addBook(Book book) {
		book.setId(UUID.randomUUID().toString());
		bookDao.save(book);
		
	}

	public Page findAllBookPageRecords(String pagenum) {
		
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.findAllBooksNumber();
		Page page = new Page(currentPageNum,totalRecords);
		
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize()));
		return page;
	}
	
	public Book findBookById(String bookId) {
		return bookDao.findById(bookId);
	}
	
	public List<Book> findBookByCategoryId(String categoryId) {
		return bookDao.findBookByCategoryId(categoryId);
	}

	public Page findAllBookPageRecords(String pagenum, String categoryId) {
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.findCategoryBooksNumber(categoryId);
		Page page = new Page(currentPageNum,totalRecords);
		
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize(),categoryId));
		return page;
	}
	
	public void delBookById(String bookId) {
		bookDao.delBookById(bookId);
	}

	public void regitsCustomer(Customer c) {
		c.setId(UUID.randomUUID().toString());
		customerDao.save(c);
	}

	public void activeCustomer(String code) {
		Customer c = customerDao.findByCode(code);
		c.setActived(true);
		customerDao.update(c);
	}

	public Customer customerLogin(String usename, String password) {
		Customer c = customerDao.findCustomer(usename,password);
		if(c==null)
			return null;
		if(!c.isActived())
			return null;
		return c;
	}

	public void genOrder(Order order) {
		if(order.getCustomer()==null)
			throw new IllegalArgumentException("订单的客户不能为空");
		order.setId(UUID.randomUUID().toString());
		orderDao.save(order);
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public Order findOrderById(String orderId) {
		
		return orderDao.findById(orderId);
	}

	public Order findOrderByOrderNum(String orderNum) {

		return orderDao.findByOrderNum(orderNum);
	}

	public List<Order> findOrdersByCustomer(Customer c) {
		return orderDao.findbyCustomerId(c.getId());
	}

	

	

	


}
