package cn.htzb.easybuy.biz.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.biz.UserService;
import cn.htzb.easybuy.dao.UserDao;
import cn.htzb.easybuy.dao.impl.UserDaoImpl;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.UniqueConstraintException;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.DataSourceUtil;

public class UserServiceImpl implements UserService {
	public User findById(String id) {//根据ID查询用户信息
		Connection connection = null;
		User user = null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			user = userDao.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return user;
	}

	public void save(User user) {//新增用户信息
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.save(user);
		} catch (SQLException e) {
				throw new UniqueConstraintException(e);
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void update(User user, String userId) {//更新用户信息
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.update(user, userId);
		} catch (SQLException e) {			
				throw new UniqueConstraintException(e);			
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void delete(String userId) {//根据ID删除用户
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.delete(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public long getUserRowCount() {//查询用户记录条数
		Connection connection = null;
		long rtn = 0;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			rtn = userDao.getUserRowCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public List<User> getUsers(Pager pager) {//查询当页用户
		Connection connection = null;
		List<User> rtn = new ArrayList<User>();
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			rtn = userDao.getUsers(pager);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	@Override
	public void addAdress(User user, String address) {//为某用户添加地址
		Connection connection = null;
		List<User> rtn = new ArrayList<User>();
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.addAdress(user,address);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	@Override
	public void setLogin(User user, boolean isLogin) {//设置用户登录状态
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.setLogin(user,isLogin);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		
	}

}
