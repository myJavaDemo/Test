package com.java.dao;

import java.util.List;

import com.java.domain.Book;

public interface BookDao {
	void save(Book book);
	/**
	 * �鼮��������
	 * @return
	 */
	int findAllBooksNumber();
	/**
	 * ��ѯ�����鼮�ķ�ҳ����
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	List<Book> findPageBooks(int startIndex,int offset);

	Book findById(String bookId);
	
	List<Book> findBookByCategoryId(String categoryId);
	/**
	 * ĳ���鼮��������
	 * @return
	 */
	int findCategoryBooksNumber(String categoryId);
	/**
	 * ���շ����ѯ�鼮�ķ�ҳ����
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	List findPageBooks(int startIndex, int pageSize, String categoryId);
	
	void delBookById(String bookId);
	
	
}
