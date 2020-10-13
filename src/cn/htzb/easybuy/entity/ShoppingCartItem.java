package cn.htzb.easybuy.entity;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable{
	private Product product;//商品
	private long quantity;//数量
	private float cost;//价格

	public ShoppingCartItem(Product product, long quantity) {
		this.product = product;
		this.quantity = quantity;
		this.cost = product.getPrice() * quantity;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
		this.cost = product.getPrice() * quantity;
	}

	public Product getProduct() {
		return product;
	}

	public float getCost() {
		return cost;
	}

}
