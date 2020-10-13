package cn.htzb.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.dao.UserDao;
import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.Validator;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	public UserDaoImpl(Connection connection) {
		super(connection);
	}

	public User findById(String id) throws SQLException {//根据ID查询用户信息
		User user = null;
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT eu_user_id,eu_user_name,eu_password,eu_sex,"
						+ "eu_birthday,eu_identity_code,eu_email,eu_mobile,"
						+ "eu_address,eu_status,eu_login FROM easybuy_user where eu_user_id='"
						+ id + "'");
		if (rs.next()) {
			user = createUserByResultSet(rs);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return user;
	}

	//由结果集生成对象
	private User createUserByResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserId(rs.getString("eu_user_id"));
		user.setUserName(rs.getString("eu_user_name"));
		user.setPassword(rs.getString("eu_password"));
		user.setMale(rs.getString("eu_sex").equals("T"));
		user.setBirthday(rs.getDate("eu_birthday"));
		user.setIdentityCode(rs.getString("eu_identity_code"));
		user.setEmail(rs.getString("eu_email"));
		user.setMobile(rs.getString("eu_mobile"));
		user.setAddress(rs.getString("eu_address"));
		user.setStatus(rs.getInt("eu_status"));		
		user.setLogin(rs.getBoolean("eu_login"));
		return user;
	}

	public void save(User user) throws SQLException {//新增用户信息
		String sql = " INSERT INTO easybuy_user(eu_user_id,eu_user_name,eu_password,eu_sex,"
				+ "eu_birthday,eu_identity_code,eu_email,eu_mobile,"
				+ "eu_address,eu_status) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getUserId());
		ps.setString(2, user.getUserName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.isMale() ? "T" : "F");
		ps.setDate(5, Validator.toSqlDate(user.getBirthday()));
		ps.setString(6, user.getIdentityCode());
		ps.setString(7, user.getEmail());
		ps.setString(8, user.getMobile());
		ps.setString(9, user.getAddress());
		ps.setInt(10, user.getStatus());
		ps.executeUpdate();
		ps.close();
	}

	//更新用户信息
	public void update(User user,String userId) throws SQLException {
		String sql = " UPDATE easybuy_user SET eu_user_name=?,eu_password=?,eu_sex=?,"
				+ "eu_birthday=?,eu_identity_code=?,eu_email=?,eu_mobile=?,"
				+ "eu_address=?,eu_status=?,eu_user_id=?  where eu_user_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getUserName());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.isMale() ? "T" : "F");
		ps.setDate(4, Validator.toSqlDate(user.getBirthday()));
		ps.setString(5, user.getIdentityCode());
		ps.setString(6, user.getEmail());
		ps.setString(7, user.getMobile());
		ps.setString(8, user.getAddress());
		ps.setInt(9, user.getStatus());
		ps.setString(10, user.getUserId());
		ps.setString(11, userId);
		ps.executeUpdate();
		ps.close();
	}

	//根据ID删除用户
	public void delete(String userId) throws SQLException {
		String sql = " DELETE FROM easybuy_user where eu_user_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, userId);
		ps.executeUpdate();
		ps.close();
	}

	//查询用户记录条数
	public long getUserRowCount() throws SQLException {
		return getRowCount("easybuy_user");
	}

	//获取本页要显示的用户
	public List<User> getUsers(Pager pager) throws SQLException {
		List<User> rtn = new ArrayList<User>();

		String sql = "select * from  easybuy_user  ";
		if (pager != null)//分页显示
			sql = this.getSqlForPages("easybuy_user", "EU_USER_ID","", pager);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createUserByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	@Override
	public void addAdress(User user, String address) {//为用户添加收货地址
		String sql = " update EASYBUY_USER  set EU_ADDRESS=EU_ADDRESS+? where EU_USER_ID=?";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			address=";"+address;
			ps.setString(1, address);
			ps.setString(2, user.getUserId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}

	@Override
	public void setLogin(User user, boolean isLogin) {//设置用户登录状态
		String sql = " update EASYBUY_USER  set EU_Login=? where EU_USER_ID=?";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setBoolean(1, isLogin);
			ps.setString(2, user.getUserId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
