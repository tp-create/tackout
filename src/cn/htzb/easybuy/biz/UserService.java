package cn.htzb.easybuy.biz;

import java.util.List;

import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.User;

public interface UserService {
	void update(User user, String userId);//更新用户信息

	User findById(String userId);//根据ID查询用户信息

	void save(User user);//新增用户信息
	
	void addAdress(User user, String address);//为某用户添加地址
	
	void delete(String userId);//根据ID删除用户

	List<User> getUsers(Pager pager);//查询当页用户

	long getUserRowCount();//查询用户记录条数
	
	void setLogin(User user, boolean isLogin);//设置用户登录状态
}
