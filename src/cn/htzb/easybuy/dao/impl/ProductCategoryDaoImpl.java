package cn.htzb.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.dao.ProductCategoryDao;
import cn.htzb.easybuy.entity.ProductCategory;

public class ProductCategoryDaoImpl extends BaseDaoImpl implements
		ProductCategoryDao {

	public ProductCategoryDaoImpl(Connection connection) {
		super(connection);
	}

	public ProductCategory findById(long id) throws SQLException {//根据ID查询商品分类
		ProductCategory productCategory = null;
		String sql = "SELECT epc_id,epc_name,epc_parent_id FROM easybuy_product_category where epc_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			productCategory = createProductCategoryByResultSet(rs);
		}
		rs.close();
		ps.close();
		return productCategory;
	}

	public List<ProductCategory> getProductCategories(Long parentId)
			throws SQLException {//根据父ID获取子商品分类
		List<ProductCategory> rtn = new ArrayList<ProductCategory>();
		String sql = "select * from  easybuy_product_category ";
		if (parentId != null)
			sql = sql + " where epc_parent_id = " + parentId;
		sql = sql + " order by epc_parent_id,epc_id";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createProductCategoryByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}
    //由结果集生成对象
	private ProductCategory createProductCategoryByResultSet(ResultSet rs)
			throws SQLException {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setId(rs.getLong("epc_id"));
		productCategory.setName(rs.getString("epc_name"));
		productCategory.setParentId(rs.getLong("epc_parent_id"));
		return productCategory;
	}

	//查询商品分类的根节点
	public List<ProductCategory> getRootCategories() throws SQLException {
		List<ProductCategory> rtn = new ArrayList<ProductCategory>();
		String sql = "select * from  easybuy_product_category epc where epc.epc_parent_id=epc.epc_id";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createProductCategoryByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	//删除商品分类
	public void delete(long id) throws SQLException {
		String sql = " DELETE FROM easybuy_product_category where epc_id=? or EPC_PARENT_ID=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.setLong(2, id);
		ps.executeUpdate();
		ps.close();
	}

	//新增商品分类
	public void save(ProductCategory productCategory) throws SQLException {
		String sql = " INSERT INTO easybuy_product_category(epc_name,epc_parent_id) values(?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, productCategory.getName());
		ps.setLong(2, productCategory.getParentId());
		ps.executeUpdate();		
		//查找当前自增长的产品种类ID
		sql="select @@IDENTITY ";
		ps = connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();	
		if(rs.next()){
			productCategory.setId(rs.getLong(1));
			if (productCategory.getParentId() == 0){//如果添加的根目录，则设置父ID为自身ID
				sql="update EASYBUY_PRODUCT_CATEGORY set EPC_PARENT_ID =EPC_ID where EPC_ID="+rs.getLong(1);
				ps = connection.prepareStatement(sql);
				ps.execute();
			}			
		}		
		ps.close();	
	}

	//更新商品分类
	public void update(ProductCategory productCategory) throws SQLException {
		String sql = " UPDATE easybuy_product_category  SET epc_name=?,epc_parent_id=? where epc_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, productCategory.getName());
		ps.setLong(2, productCategory.getParentId());
		ps.setLong(3, productCategory.getId());
		ps.executeUpdate();
		ps.close();
	}
}
