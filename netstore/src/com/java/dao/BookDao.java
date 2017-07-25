package com.java.dao;

import java.util.List;

import com.java.domain.Book;

public interface BookDao {
	void save(Book book);
	/**
	 * 书籍的总条数
	 * @return
	 */
	int findAllBooksNumber();
	/**
	 * 查询所有书籍的分页数据
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	List<Book> findPageBooks(int startIndex,int offset);

	Book findById(String bookId);
	
	List<Book> findBookByCategoryId(String categoryId);
	/**
	 * 某类书籍的总条数
	 * @return
	 */
	int findCategoryBooksNumber(String categoryId);
	/**
	 * 按照分类查询书籍的分页数据
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	List findPageBooks(int startIndex, int pageSize, String categoryId);
	
	void delBookById(String bookId);
	
	
}
