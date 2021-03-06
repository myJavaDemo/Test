package com.java.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.java.domain.Category;



public class FillBeanUtil {

	public static <T> T fillBean(HttpServletRequest request,Class<T> clazz){
			try {
				T bean = clazz.newInstance();
				BeanUtils.copyProperties(bean, request.getParameterMap());
				return bean;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		
	}
}
