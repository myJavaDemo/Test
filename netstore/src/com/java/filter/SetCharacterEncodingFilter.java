package com.java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class SetCharacterEncodingFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String encoding = filterConfig.getInitParameter("encoding");
		if(encoding==null){
			encoding = "UTF-8";
		}
		request.setCharacterEncoding(encoding);//POSTÇëÇó²ÎÊý
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset=UTF-8");
		chain.doFilter(request, response);		
	}

	private FilterConfig filterConfig;
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
