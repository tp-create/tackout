package cn.htzb.easybuy.biz.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.dao.CommentDao;
import cn.htzb.easybuy.dao.ProductCategoryDao;
import cn.htzb.easybuy.dao.ProductDao;
import cn.htzb.easybuy.dao.impl.CommentDaoImpl;
import cn.htzb.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.htzb.easybuy.dao.impl.ProductDaoImpl;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.util.DataSourceUtil;
import cn.htzb.easybuy.util.Validator;

public class ProductServiceImpl implements ProductService {

	public void delete(String id) {//删除商品
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			productDao.delete(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void deleteCategory(String id) {//根据ID删除商品分类
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao=new ProductDaoImpl(connection);
			Integer parentid=Integer.valueOf(id);
			productDao.updateChildID(parentid); //更新此种类节点下的商品信息	
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			productCategoryDao.delete(Long.parseLong(id));//删除商品的种类节点

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public Product findById(String id) {//根据ID查询商品
		Connection connection = null;
		Product product = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			product = productDao.findById(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return product;
	}

	public ProductCategory findCategoryById(String id) {//根据ID查询商品分类
		Connection connection = null;
		ProductCategory productCategory = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			productCategory = productCategoryDao.findById(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return productCategory;
	}

	public List<ProductCategory> getProductCategories(String parentId) {//根据父ID查询所有子商品分类
		Connection connection = null;
		Long id = null;
		if (!Validator.isEmpty(parentId))
			id = Long.parseLong(parentId);
		List<ProductCategory> rtn = new ArrayList<ProductCategory>();
		try {
			connection = DataSourceUtil.openConnection();
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			rtn = productCategoryDao.getProductCategories(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public List<Product> getProductsByCategory(String categoryId, Pager pager) {//根据分类查询商品
		Connection connection = null;
		List<Product> rtn = new ArrayList<Product>();
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			if (categoryId == null)
				rtn = productDao.getProductsByCategoryLevelTwo(null, pager);
			else
				rtn = productDao.getProductsByCategoryLevelTwo(Long
						.parseLong(categoryId), pager);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public Long save(Product product) {//保存一款商品
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			return productDao.save(product);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return null;
	}

	public void saveCategory(ProductCategory productCategory) {//增加某商品分类
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			productCategoryDao.save(productCategory);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void update(Product product) {//更新商品
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			productDao.update(product);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void updateCategory(ProductCategory productCategory, String type) {//更新商品分类
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			ProductDao productDao = new ProductDaoImpl(connection);
			String sql = null;
			if (type.equals("ModifyParent")) {
				sql = " UPDATE easybuy_product SET epc_id="
						+ productCategory.getParentId()
						+ " where epc_child_id=" + productCategory.getId();
			} else {
				sql = " UPDATE easybuy_product SET epc_child_id = null,epc_id="
						+ productCategory.getId() + " where epc_child_id="
						+ productCategory.getId();
			}
			connection.setAutoCommit(false);
			try {
				productCategoryDao.update(productCategory);
				productDao.modifyCategoryOfProductBySql(sql);
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				connection.rollback();
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public List<ProductCategory> getRootCategories() {//查询商品分类的根节点
		Connection connection = null;
		List<ProductCategory> rtn = new ArrayList<ProductCategory>();
		try {
			connection = DataSourceUtil.openConnection();
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			rtn = productCategoryDao.getRootCategories();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public void updateCategoryName(ProductCategory productCategory) {//更新商品分类
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(
					connection);
			productCategoryDao.update(productCategory);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public long getProductRowCount(String categoryId) {//查询商品记录数
		Connection connection = null;
		long rtn = 0;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			Long id = null;
			if (categoryId != null)
				id = Long.parseLong(categoryId);
			rtn = productDao.getProductRowCountByCategoryLevelTwo(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	@Override
	public List<Product> getProductsByCategory(String categoryId, Pager pager,
											   String condition) {//根据分类查询商品，带条件查询
		Connection connection = null;
		List<Product> rtn = new ArrayList<Product>();
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			if (categoryId == null)
				rtn = productDao.getProductsByCategoryLevelTwo(null, pager,condition);
			else
				rtn = productDao.getProductsByCategoryLevelTwo(Long
						.parseLong(categoryId), pager,condition);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	@Override
	public long getProductRowCount(String categoryId, String condition) {//查询商品记录数
		Connection connection = null;
		long rtn = 0;
		try {
			connection = DataSourceUtil.openConnection();
			ProductDao productDao = new ProductDaoImpl(connection);
			Long id = null;
			if (categoryId != null)
				id = Long.parseLong(categoryId);
			rtn = productDao.getProductRowCountByCategoryLevelTwo(id,condition);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

}
