package cn.htzb.easybuy.dao;

import java.sql.SQLException;
import java.util.List;

import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.User;

public interface UserDao {

	User findById(String id) throws SQLException;//根据ID查询用户信息

	void save(User user) throws SQLException;//新增用户信息

	void update(User user, String userId) throws SQLException;//更新用户信息

	void delete(String userId)  throws SQLException;//根据ID删除用户

	long getUserRowCount()  throws SQLException;//查询用户记录条数

	List<User> getUsers(Pager pager)  throws SQLException;//获取本页要显示的用户
	void addAdress(User user, String address);//为用户添加收货地址

	void setLogin(User user, boolean isLogin);//设置用户登录状态

}
