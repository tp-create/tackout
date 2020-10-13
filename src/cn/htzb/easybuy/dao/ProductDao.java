package cn.htzb.easybuy.dao;

import java.sql.SQLException;
import java.util.List;

import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;

public interface ProductDao {

	Product findById(Long id) throws SQLException;//根据ID查询商品

	//根据分类查询商品
	List<Product> getProductsByCategoryLevelOne(Long id) throws SQLException;

	//根据分类查询本页显示的商品
	List<Product> getProductsByCategoryLevelTwo(Long id, Pager pager)
			throws SQLException;
	//根据分类查询本页显示的商品且带条件查询
	List<Product> getProductsByCategoryLevelTwo(Long id, Pager pager, String condition)
			throws SQLException;
	
	//更新某款商品的分类
	void modifyCategoryOfProductBySql(String sql) throws SQLException;

	public void delete(Long id) throws SQLException;//删除一款商品
	
	Long save(Product product) throws SQLException;//保存一款商品

	void update(Product product) throws SQLException;//更新一款商品
	
	void updateChildID(int parentid) throws SQLException;//更新商品的二级分类
	

	//查询所有商品记录数
	long getProductRowCountByCategoryLevelTwo(Long categoryId) throws SQLException;
	//查询带条件的商品记录数
	long getProductRowCountByCategoryLevelTwo(Long categoryId, String condition) throws SQLException;

	//更新价格
	void updateStock(Long id, long quantity) throws SQLException;
}
