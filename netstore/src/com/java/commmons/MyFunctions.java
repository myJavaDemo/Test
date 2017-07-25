package com.java.commmons;

import com.java.domain.Category;
import com.java.service.BusinessService;
import com.java.service.impl.BusinessServiceImpl;

public class MyFunctions {
	private static BusinessService s = new BusinessServiceImpl();
	public static String showCategoryName(String categoryId){
		Category c = s.findCategoryById(categoryId);
		if(c!=null){
			return c.getName();
		}
		return null;
	}
}
