package com.java.dao;

import java.util.List;

import com.java.domain.Category;

public interface CategoryDao {

	void save(Category category);

	List<Category> findAll();

	Category findById(String categoryId);

	void delById(String categoryId);
}
