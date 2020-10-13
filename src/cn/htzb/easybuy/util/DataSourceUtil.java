package cn.htzb.easybuy.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtil {
	private static DataSource dataSource;

	//获取连接
	public static Connection openConnection() throws SQLException {
		return dataSource.getConnection();
	}
	//关闭连接
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//为连接做准备
	static {
		Context ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/easybuy");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
