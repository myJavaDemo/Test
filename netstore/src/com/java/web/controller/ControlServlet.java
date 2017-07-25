package com.java.web.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.java.commmons.Page;
import com.java.domain.Book;
import com.java.domain.Category;
import com.java.service.BusinessService;
import com.java.service.impl.BusinessServiceImpl;
import com.java.util.FillBeanUtil;

public class ControlServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if("addCategory".equals(op)){
			addCategory(request,response);
		}else if("showAllCategorys".equals(op)){
			showAllCategorys(request,response);
		}else if("showAddBookUI".equals(op)){
			showAddBookUI(request,response);
		}else if("addBook".equals(op)){
			try {
				addBook(request,response);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else if("showAllBooks".equals(op)){
			showAllBooks(request,response);
		}else if("delCategoryById".equals(op)){
			delCategoryById(request,response);
		}else if("delBookById".equals(op)){
			delBookById(request,response);
		}
		
	}

	
	private void delBookById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("bookId");
		s.delBookById(id);
		request.setAttribute("msg", "删除成功");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}


	private void delCategoryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("categoryId");
		List<Book> list = s.findBookByCategoryId(id);
		if(!list.isEmpty()){
			request.setAttribute("msg","请先删除这个分类的图书后再删除这个分类");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
			return;
		}
		s.delCategoryById(id);
		request.setAttribute("msg", "删除成功");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}


	private void showAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/servlet/ControlServlet?op=showAllBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBook.jsp").forward(request, response);
	}


	private void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isMultipart =  ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			request.setAttribute("msg","哥们，你的表单有误，请检查");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items = sfu.parseRequest(request);
		
		Book book = new Book();
		for (FileItem item : items) {
			if(item.isFormField()){
				String fileName = item.getFieldName();
				String fileValue = item.getString(request.getCharacterEncoding());
				BeanUtils.setProperty(book, fileName, fileValue);
			}else{
				String fileName = item.getName();
				if(fileName!=null&&!fileName.trim().equals("")){
					fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
					String storeDirectory = getServletContext().getRealPath("/images");
					String path = makeDirs(storeDirectory,fileName);
					
					book.setPath(path);
					book.setPhotoFileName(fileName);
					
					item.write(new File(storeDirectory+path+"/"+fileName));
				}
			}
		}
		s.addBook(book);
		request.setAttribute("msg","书籍保存成功！");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
		
	}


	private String makeDirs(String storeDirectory, String fileName) {
		int hashCode = fileName.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0)>>4;
		
		String newPath = "/"+dir1+"/"+dir2;
		File file = new File(storeDirectory, newPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return newPath;
	}


	private void showAddBookUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> list = s.findAllCategories();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}


	private void showAllCategorys(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> list = s.findAllCategories();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/manage/listCategory.jsp").forward(request, response);
	}


	private void addCategory(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException  {
		Category category = FillBeanUtil.fillBean(request, Category.class);
		s.addCategory(category);
		
		request.setAttribute("msg", "保存陈功");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	doGet(request,response);
	}

}
