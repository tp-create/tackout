package cn.htzb.easybuy.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable{
	public List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();	
	private int payType = Order.PAYTTYPE_CASH;

	//获取购物车中所有商品
	public List<ShoppingCartItem> getItems() {
		return items;
	}	
	//添加一项
	public void addItem(Product product, long quantity) {
		for(int i=0;i<items.size();i++){
			//判断购物车中是否已有此商品，如果有则累计数量
			if((items.get(i).getProduct().getId()).equals(product.getId())){
				items.get(i).setQuantity(items.get(i).getQuantity()+quantity);
				return;
			}
		}
		items.add(new ShoppingCartItem(product, quantity));
	}

	//移除一项
	public void removeItem(int index) {
		items.remove(index);
	}

	//修改数量
	public void modifyQuantity(int index, long quantity) {
		items.get(index).setQuantity(quantity);
	}

	//计算总价格
	public float getTotalCost() {
		float sum = 0;
		for (ShoppingCartItem item : items) {
			sum = sum + item.getCost();
		}
		return sum;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
}
