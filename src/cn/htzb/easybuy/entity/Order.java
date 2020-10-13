package cn.htzb.easybuy.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	//订单状态
	public static int STATUS_INITIAL = 1;
	public static int STATUS_PASS = 2;
	public static int STATUS_PREPARE = 3;
	public static int STATUS_SEND = 4;
	public static int STATUS_RECEIVED = 5;

	public static int PAYTTYPE_CASH = 1;
	public static int PAYTYPE_NET = 2;

	private int id;//ID
	private String userId;//登录名
	private String userName;//真实姓名
	private String userAddress;//收货地址
	private Date createTime;//创建时间
	private Float cost;//订单总计价格
	private int status;//订单状态
	private int payType;//付款方式

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getDisplayStatus() {
		switch (this.status) {
		case 1:
			return "待审核";
		case 2:
			return "审核通过";
		case 3:
			return "配货";
		case 4:
			return "卖家已发货";
		case 5:
			return "已收货";
		default:
			return "待审核";
		}

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	@Override
	public int hashCode() {		
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Order){
			if(((Order)obj).id==id){
				return true;
			}			
		}	
		return false;
	}
	
}
