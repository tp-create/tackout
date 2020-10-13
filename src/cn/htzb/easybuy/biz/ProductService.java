package cn.htzb.easybuy.biz;

import java.util.List;

import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;

public interface ProductService {

	Product findById(String id);//根据ID查询商品

	Long save(Product product);//保存一款商品

	void update(Product product);//更新商品

	void delete(String id);//删除商品
	
	//根据分类查询商品
	List<Product> getProductsByCategory(String categoryId, Pager pager);
	List<Product> getProductsByCategory(String categoryId, Pager pager, String condition);
	
	ProductCategory findCategoryById(String id);//根据ID查询商品分类

	void deleteCategory(String id);//根据ID删除商品分类

	List<ProductCategory> getProductCategories(String parentId);//根据父ID查询所有子商品分类

	void saveCategory(ProductCategory productCategory);//增加某商品分类

	void updateCategory(ProductCategory productCategory, String type);//更新商品分类

	List<ProductCategory> getRootCategories();//查询商品分类的根节点

	void updateCategoryName(ProductCategory productCategory);//更新商品分类
	
	//查询商品记录数
	long getProductRowCount(String categoryId);
	long getProductRowCount(String categoryId, String condition);

}
