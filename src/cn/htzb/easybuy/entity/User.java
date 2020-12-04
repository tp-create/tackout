package cn.htzb.easybuy.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	public static int USER_TYPE_ORDINARY = 1;
	public static int USER_TYPE_ADMIN = 2;

	private String userId;//ID
	private String userName;//用户名
	private String password;//密码
	private boolean male;//性别
	private Date birthday;//出生日期
	private String identityCode;
	private String email;//电子邮箱
	private String mobile;//电话
	private String address;//地址
	private int status = 1;
	private boolean login;//ID

	public boolean getLogin() {
		return login;
	}

	public void setLogin(boolean isLogin) {
		this.login = isLogin;
	}

	public boolean isAdministrator() {
		return (this.status == USER_TYPE_ADMIN);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
