package com.java.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.java.Exception.DaoException;
import com.java.dao.OrderDao;
import com.java.domain.Order;
import com.java.domain.OrderItem;
import com.java.util.DBCPUtil;

public class OrderDaoImpl implements OrderDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	public void save(Order order) {
		try {
			qr.update("insert into orders (id,orderNum,quantity,amount,status,customersId) values(?,?,?,?,?,?)", 
					order.getId(),
					order.getOrderNum(),
					order.getQuantity(),
					order.getAmount(),
					order.getStatus(),
					order.getCustomer().getId());
			
			List<OrderItem> items = order.getItems();
			if(items.size()>0){
				for(OrderItem item:items){
					qr.update("insert into orders_items (id,quantity,price,booksId,ordersId) values(?,?,?,?,?)", 
							item.getId(),
							item.getQuantity(),
							item.getPrice(),
							item.getBook().getId(),
							order.getId()
							);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public void update(Order order) {
		try {
			qr.update("update  orders set orderNum=?,quantity=?,amount=?,status=? where id=? ", 
					order.getOrderNum(),
					order.getQuantity(),
					order.getAmount(),
					order.getStatus(),
					order.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	@Override
	public Order findById(String orderId) {
		
		try {
			return qr.query("select * from orders where id=?",new BeanHandler<Order>(Order.class),orderId);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public Order findByOrderNum(String orderNum) {

		try {
			return qr.query("select * from orders where orderNum=?",new BeanHandler<Order>(Order.class),orderNum);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Order> findbyCustomerId(String id) {
		try {
			return qr.query("select * from orders where customersId=? order by orderNum desc", new BeanListHandler<Order>(Order.class),id);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	

}
