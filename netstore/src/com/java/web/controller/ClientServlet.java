package com.java.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.commmons.Page;
import com.java.domain.Book;
import com.java.domain.Category;
import com.java.domain.Customer;
import com.java.domain.Order;
import com.java.domain.OrderItem;
import com.java.service.BusinessService;
import com.java.service.impl.BusinessServiceImpl;
import com.java.util.FillBeanUtil;
import com.java.util.IdGenertor;
import com.java.util.PaymentUtil;
import com.java.util.SendMail;

import www.java.web.beans.Cart;
import www.java.web.beans.CartItem;

public class ClientServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if("showIndex".equals(op)){
			showIndex(request,response);
		}else if("showCategoryBooks".equals(op)){
			showCategoryBooks(request,response);
		}else if("showBookDetails".equals(op)){
			showBookDetails(request,response);
		}else if("buyBook".equals(op)){
			buyBook(request,response);
		}else if("changeNum".equals(op)){
			changeNum(request,response);
		}else if("delOneItem".equals(op)){
			delOneItem(request,response);
		}else if("registCustomer".equals(op)){
			registCustomer(request,response);
		}else if("active".equals(op)){
			active(request,response);
		}else if("login".equals(op)){
			login(request,response);
		}else if("logout".equals(op)){
			logout(request,response);
		}else if("genOrder".equals(op)){
			genOrder(request,response);
		}else if("showCustomerOrders".equals(op)){
			showCustomerOrders(request,response);
		}else if("pay".equals(op)){
			pay(request,response);
		}
	
	}


	private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNum = request.getParameter("order");
		String money = request.getParameter("money");//支付金额
		String pd_FrpId = request.getParameter("pd_FrpId");//银行
		
		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";
		String p2_Order = orderNum;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "unknown";
		String p6_Pcat = "unknown";
		String p7_Pdesc = "unknown";
		String p8_Url = "http://localhost:8080/netstore/servlet/PaymentResponse";
		String p9_SAF = "1";
		String pa_MP = "no";
		String pr_NeedResponse = "1";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		
		request.setAttribute("p0_Cmd",p0_Cmd );
		request.setAttribute("p1_MerId",p1_MerId );
		request.setAttribute("p2_Order",p2_Order );
		request.setAttribute("p3_Amt",p3_Amt );
		request.setAttribute("p4_Cur",p4_Cur );
		request.setAttribute("p5_Pid",p5_Pid );
		request.setAttribute("p6_Pcat",p6_Pcat );
		request.setAttribute("p7_Pdesc",p7_Pdesc );
		request.setAttribute("p8_Url",p8_Url );
		request.setAttribute("p9_SAF",p9_SAF );
		request.setAttribute("pa_MP",pa_MP );
		request.setAttribute("pr_NeedResponse",pr_NeedResponse );
		request.setAttribute("pd_FrpId",pd_FrpId );
		request.setAttribute("hmac",hmac );
		
		request.getRequestDispatcher("/sure.jsp").forward(request, response);
	}


	private void showCustomerOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("customer");
		if(c==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		List<Order> orders = s.findOrdersByCustomer(c);
		request.setAttribute("os", orders);
		request.getRequestDispatcher("/listOrders.jsp").forward(request, response);
	}


	private void genOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		Customer c = (Customer)session.getAttribute("customer");
		if(c==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		Cart cart = (Cart)session.getAttribute("cart");
		Order order = new Order();
		String orderNum = IdGenertor.genOrdersNum();
		order.setOrderNum(orderNum);
		order.setQuantity(cart.getTotalQuantity());
		order.setAmount(cart.getAmount());
		order.setCustomer(c);
		
		Map<String,CartItem> items = cart.getItems();
		for(Map.Entry<String, CartItem> me : items.entrySet()){
			OrderItem oi = new OrderItem();
			oi.setId(UUID.randomUUID().toString());
			oi.setQuantity(me.getValue().getQuantity());
			oi.setPrice(me.getValue().getTotalPrice());
			oi.setBook(me.getValue().getBook());
			order.getItems().add(oi);
		}
		
		s.genOrder(order);
		request.getRequestDispatcher("/pay.jsp?orderNum="+orderNum+"&amount="+order.getAmount()).forward(request, response);
	}


	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("customer");
		response.sendRedirect(request.getContextPath());
	}


	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String usename = request.getParameter("usename");
		String password = request.getParameter("password");
		Customer c = s.customerLogin(usename, password);
		if(c!=null){
			request.getSession().setAttribute("customer", c);
			response.sendRedirect(request.getContextPath());
		}else{
			request.setAttribute("msg", "您的用户名、密码不正确，或者没有激活账户");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}


	private void active(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String code = request.getParameter("code");
		if(code!=null&&!"".equals(code)){
			s.activeCustomer(code);
			response.getWriter().write("激活成功！2秒后自动转向主页");
			response.setHeader("Refresh", "2;URL="+request.getContextPath());
		}else{
			request.setAttribute("msg", "激活码有误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}


	private void registCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Customer c = FillBeanUtil.fillBean(request, Customer.class);
		String code = UUID.randomUUID().toString();
		c.setCode(code);
		SendMail sm = new SendMail(c);
		sm.start();
		
		s.regitsCustomer(c);
		request.setAttribute("msg", "注册成功！我们已经发送了一封激活邮件到您的"+c.getEmail()+"邮箱中，请及时激活您的账户");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}


	private void delOneItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookId = request.getParameter("bookId");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.getItems().remove(bookId);
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}


	private void changeNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookId = request.getParameter("bookId");
		String newNum = request.getParameter("num");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		CartItem item = cart.getItems().get(bookId);
		item.setQuantity(Integer.parseInt(newNum));
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}


	private void buyBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		cart.addBook(book);
		request.setAttribute("msg", "书籍以放入购物车,<a href='"+request.getContextPath()+"'>继续购物</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}


	private void showBookDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/showDetails.jsp").forward(request, response);
	}


	private void showCategoryBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		String categoryId = request.getParameter("categoryId");
		
		List<Category> list = s.findAllCategories();
		
		Page page = s.findAllBookPageRecords(num, categoryId);
		page.setUrl("/servlet/ClientServlet?op=showCategoryBooks&categoryId="+categoryId);
		
		request.setAttribute("list", list);
		request.setAttribute("page",page);
		request.getRequestDispatcher("/listAllBooks.jsp").forward(request, response);
	}


	private void showIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String num = request.getParameter("num");
		
		List<Category> list = s.findAllCategories();
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/servlet/ClientServlet?op=showIndex");
		
		request.setAttribute("list", list);
		request.setAttribute("page",page);
		request.getRequestDispatcher("/listAllBooks.jsp").forward(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
