package cn.htzb.easybuy.biz.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.htzb.easybuy.biz.OrderService;
import cn.htzb.easybuy.dao.OrderDao;
import cn.htzb.easybuy.dao.ProductCategoryDao;
import cn.htzb.easybuy.dao.ProductDao;
import cn.htzb.easybuy.dao.impl.OrderDaoImpl;
import cn.htzb.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.htzb.easybuy.dao.impl.ProductDaoImpl;
import cn.htzb.easybuy.entity.Order;
import cn.htzb.easybuy.entity.OrderDetail;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.entity.ShoppingCart;
import cn.htzb.easybuy.entity.ShoppingCartItem;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.DataSourceUtil;

public class OrderServiceImpl implements OrderService {

	public void payShoppingCart(ShoppingCart cart, User user,String address) {//购物
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			ProductDao productDao = new ProductDaoImpl(connection);
			connection.setAutoCommit(false);
			try {
				//更新订单
				Order order = new Order();
				order.setUserId(user.getUserId());
				order.setUserName(user.getUserName());
				order.setUserAddress(address);
				order.setCreateTime(new Date());
				order.setCost(cart.getTotalCost());
				order.setPayType(Order.PAYTTYPE_CASH);
				order.setStatus(Order.STATUS_INITIAL);
				orderDao.saveOrder(order);
				for (ShoppingCartItem item : cart.getItems()) {
					//更新订单详情
					OrderDetail detail = new OrderDetail();
					detail.setOrderId(order.getId());
					detail.setCost(item.getCost());
					detail.setProduct(item.getProduct());
					detail.setQuantity(item.getQuantity());
					orderDao.saveOrderDetail(detail, order.getId());
					productDao.updateStock(item.getProduct().getId(), item
							.getQuantity());
				}
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				connection.rollback();
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void delete(String id) {//删除订单
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			connection.setAutoCommit(false);
			try {
				orderDao.deleteOrder(Long.parseLong(id));
				orderDao.deleteOrderDetails(Long.parseLong(id));
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				connection.rollback();
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public Order findById(String id) {//根据ID获取订单
		Connection connection = null;
		Order order = null;
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			order = orderDao.findById(Integer.valueOf(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return order;
	}

	public long getOrderRowCount(String condition) {//获取订单记录条数
		Connection connection = null;
		long rtn = 0;
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			rtn = orderDao.getOrderRowCount(condition);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public Map <Order,ArrayList<Product>> getOrders(String condition, Pager pager) {//获取多条订单并带有详情
		Connection connection = null;
		Map <Order,ArrayList<Product>> rtn = new HashMap <Order,ArrayList<Product>>();
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			rtn = orderDao.getOrders(condition, pager);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public void saveOrder(Order order) {//保存订单
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			orderDao.saveOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void updateOrder(Order order) {//更新订单
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			orderDao.updateOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}
}
