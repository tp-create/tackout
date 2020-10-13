package cn.htzb.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.htzb.easybuy.dao.OrderDao;
import cn.htzb.easybuy.entity.Order;
import cn.htzb.easybuy.entity.OrderDetail;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.util.Validator;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {

	public OrderDaoImpl(Connection connection) {
		super(connection);
	}

	public void saveOrder(Order order) throws SQLException {//保存订单
		String sql = " INSERT INTO easybuy_order(eo_user_id,eo_create_time,eo_cost,"
				+ "eo_status,eo_type,eo_user_name,eo_user_address) VALUES(?,now(),?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, order.getUserId());
		//ps.setDate(2, Validator.toSqlDate(order.getCreateTime()));
		ps.setFloat(2, order.getCost());
		ps.setInt(3, order.getStatus());
		ps.setInt(4, order.getPayType());
		ps.setString(5, order.getUserName());
		ps.setString(6, order.getUserAddress());
		ps.executeUpdate();
		//查找当前自增长的定单ID
		sql="select @@IDENTITY ";
		ps = connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();	
		if(rs.next()){
			order.setId(rs.getInt(1));
		}		
		ps.close();		
	}

	public void saveOrderDetail(OrderDetail detail, int orderId)
			throws SQLException {//保存订单详情
		String sql = " INSERT INTO easybuy_order_detail(eo_id,ep_id,eod_quantity,"
				+ "eod_cost) VALUES(?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, orderId);
		ps.setLong(2, detail.getProduct().getId());
		ps.setLong(3, detail.getQuantity());
		ps.setFloat(4, detail.getCost());
		ps.executeUpdate();
		//查找当前自增长的明细ID
		sql="select @@IDENTITY ";
		ps = connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();	
		if(rs.next()){
			detail.setId(rs.getLong(1));
		}		
		ps.close();	
	}

	public void deleteOrder(long id) throws SQLException {//删除订单
		String sql = " DELETE FROM easybuy_order WHERE eo_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		ps.close();
	}

	public void deleteOrderDetails(long orderId) throws SQLException {//删除订单详情
		String sql = " DELETE FROM easybuy_order_detail WHERE eo_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, orderId);
		ps.executeUpdate();
		ps.close();
	}

	public Order findById(int id) throws SQLException {//根据ID获取订单
		Order order = null;
		String sql = "SELECT eo_id,eo_user_id,eo_create_time,eo_cost,eo_status,eo_type,eo_user_name,eo_user_address FROM easybuy_order WHERE eo_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			order = createOrderByResultSet(rs);
		}
		rs.close();
		ps.close();
		return order;
	}

	private Order createOrderByResultSet(ResultSet rs) throws SQLException {//由结果集生成对象
		Order order = new Order();
		order.setId(rs.getInt("eo_id"));
		order.setUserId(rs.getString("eo_user_id"));
		order.setCreateTime(rs.getDate("eo_create_time"));
		order.setCost(rs.getFloat("eo_cost"));
		order.setStatus(rs.getInt("eo_status"));
		order.setPayType(rs.getInt("eo_type"));
		order.setUserName(rs.getString("eo_user_name"));
		order.setUserAddress(rs.getString("eo_user_address"));
		return order;
	}

	public long getOrderRowCount(String condition) throws SQLException {//获取订单共有多少条记录
		return getRowCount("easybuy_order", condition);
	}

	//获取订单及其下属的详情记录
	public Map <Order,ArrayList<Product>> getOrders(String condition, Pager pager)
			throws SQLException {
		List<Order> rtn = new ArrayList<Order>();
		String sql = "select * from  easybuy_order  order by eo_create_time desc";
		if (pager != null){
			String file=" EASYBUY_PRODUCT.EP_ID,EASYBUY_ORDER.EO_COST,EASYBUY_ORDER.eo_id,EASYBUY_ORDER.EO_STATUS,EO_CREATE_TIME,EP_NAME,EP_PRICE ,EOD_QUANTITY, EASYBUY_PRODUCT.EP_FILE_NAME ";
			String tableName1=" EASYBUY_PRODUCT ";
			String tableName2=" EASYBUY_ORDER_DETAIL,EASYBUY_ORDER  ";
			String key=" EASYBUY_ORDER_DETAIL.EOD_ID ";			
			String orderBy=" order by EASYBUY_ORDER.EO_CREATE_TIME desc ";
			String orderBy2=" order by EASYBUY_ORDER.EO_CREATE_TIME desc ";	
			String where="";
			String where2="where EASYBUY_ORDER_DETAIL.EO_ID=EASYBUY_ORDER.EO_ID ";
			if(condition!=null && !condition.equals("")){//带条件查询				
				where="  EASYBUY_ORDER.EO_ID=EASYBUY_ORDER_DETAIL.EO_ID and EASYBUY_ORDER_DETAIL.EP_ID= EASYBUY_PRODUCT.EP_ID and "+condition;				
				where2=where2+" and "+condition;
			}else{
				where="  EASYBUY_ORDER.EO_ID=EASYBUY_ORDER_DETAIL.EO_ID and EASYBUY_ORDER_DETAIL.EP_ID= EASYBUY_PRODUCT.EP_ID ";	
			}
			sql = this.getSqlForPages(tableName1, tableName2, file,key, where,where2,orderBy,orderBy2, pager);
		}
		System.out.println("sql:"+sql);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		Map <Order,ArrayList<Product>>map=new HashMap<Order,ArrayList<Product>>();
		Order order;
		while (rs.next()) {
			order= new Order();//订单
			order.setCost(rs.getFloat("eo_cost"));
			order.setId(rs.getInt("eo_id"));
			order.setStatus(rs.getInt("eo_status"));
			order.setCreateTime(rs.getDate("eo_create_time"));
			Product product=new Product();//订单详情
			product.setId(rs.getLong("ep_id"));
			product.setName(rs.getString("ep_name"));
			product.setPrice(rs.getFloat("ep_price"));
			product.setFileName(rs.getString("ep_file_name"));
			product.setStock(rs.getLong("eod_quantity"));
			if(map.get(order)==null){
				ArrayList list=new ArrayList();
				list.add(product);
				map.put(order,list);
			}else{
				ArrayList list=map.get(order);
				list.add(product);
				map.put(order, list);
			}			
		}
		rs.close();
		prepareStatement.close();
		return map;
	}

	public void updateOrder(Order order) throws SQLException {//更新订单
		String sql = " UPDATE easybuy_order  SET eo_user_id=?,eo_create_time=?,eo_cost=?,"
				+ "eo_status=?,eo_type=?,eo_user_name=?,eo_user_address=? WHERE eo_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, order.getUserId());
		ps.setDate(2, Validator.toSqlDate(order.getCreateTime()));
		ps.setFloat(3, order.getCost());
		ps.setInt(4, order.getStatus());
		ps.setInt(5, order.getPayType());
		ps.setString(6, order.getUserName());
		if (order.getUserAddress() == null)
			ps.setNull(7, Types.VARCHAR);
		else
			ps.setString(7, order.getUserAddress());
		ps.setLong(8, order.getId());
		ps.executeUpdate();
		ps.close();
	}	
}
