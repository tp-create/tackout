package cn.htzb.easybuy.dao;

import java.sql.SQLException;
import java.util.List;

import cn.htzb.easybuy.entity.ProductCategory;

public interface ProductCategoryDao {

	ProductCategory findById(long id) throws SQLException;//根据ID查询商品分类

	List<ProductCategory> getProductCategories(Long parentId)
			throws SQLException;//根据父ID获取子商品分类

	List<ProductCategory> getRootCategories() throws SQLException;//查询商品分类的根节点

	void delete(long parseLong) throws SQLException;//删除商品分类

	void save(ProductCategory productCategory) throws SQLException;//新增商品分类

	void update(ProductCategory productCategory) throws SQLException;//更新商品分类
}
