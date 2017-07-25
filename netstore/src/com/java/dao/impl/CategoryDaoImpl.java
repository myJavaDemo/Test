package com.java.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.java.Exception.DaoException;
import com.java.dao.CategoryDao;
import com.java.domain.Category;
import com.java.util.DBCPUtil;

public class CategoryDaoImpl implements CategoryDao {

	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public void save(Category category) {
		try {
			qr.update("insert into categorys (id,name,description) values(?,?,?)", category.getId(),category.getName(),category.getDescription());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public List<Category> findAll() {
		try {
			return qr.query("select * from categorys", new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Category findById(String categoryId) {
		try {
			return qr.query("select * from categorys where id=?", new BeanHandler<Category>(Category.class),categoryId);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public void delById(String categoryId) {
		try {
			qr.update("delete from categorys where id=?", categoryId);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
