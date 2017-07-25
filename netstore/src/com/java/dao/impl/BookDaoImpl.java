package com.java.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.java.Exception.DaoException;
import com.java.dao.BookDao;
import com.java.domain.Book;
import com.java.util.DBCPUtil;

public class BookDaoImpl implements BookDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public void save(Book book) {
		try {
			qr.update("insert into books(id,name,author,price,path,photoFileName,description,categoryId) values(?,?,?,?,?,?,?,?)", 
				book.getId(),
				book.getName(),
				book.getAuthor(),
				book.getPrice(),
				book.getPath(),
				book.getPhotoFileName(),
				book.getDescription(),
				book.getCategoryId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public Book findById(String bookId) {
		try{
			return qr.query("select * from books where id=?", new BeanHandler<Book>(Book.class),bookId);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	public List<Book> findBookByCategoryId(String categoryId) {
		try{
			return qr.query("select * from books where categoryId=?", new BeanListHandler<Book>(Book.class),categoryId);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}

	public int findAllBooksNumber() {
		Object obj;
		try {
			obj = qr.query("select count(*) from books", new ScalarHandler(1));
			Long num = (Long)obj;
			return num.intValue();
		} catch (Exception e){
			throw new DaoException(e);
		}
	}



	public List<Book> findPageBooks(int startIndex, int offset) {
		try{
			return qr.query("select * from books limit ?,?", new BeanListHandler<Book>(Book.class),startIndex,offset);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	public int findCategoryBooksNumber(String categoryId) {
		try {
			Object obj = qr.query("select count(*) from books where categoryId=?", new ScalarHandler(1),categoryId);
			Long num = (Long)obj;
			return num.intValue();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List findPageBooks(int startIndex, int pageSize, String categoryId) {
		try {
			return qr.query("select * from books where categoryId=? limit ?,?", new BeanListHandler<Book>(Book.class),categoryId,startIndex,pageSize);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public void delBookById(String bookId) {
		try {
			//不检查外链，设置FOREIGN_KEY_CHECKS变量：
			qr.update("SET FOREIGN_KEY_CHECKS = 0");
			qr.update("delete from books where id=?",bookId);
			qr.update("SET FOREIGN_KEY_CHECKS = 1;");
			/*ps：关闭唯一性校验 
			set unique_checks=0; 
			set unique_checks=1;*/
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	

}
