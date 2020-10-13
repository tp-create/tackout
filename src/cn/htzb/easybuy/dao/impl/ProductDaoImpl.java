package cn.htzb.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.dao.ProductDao;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;

public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

	public ProductDaoImpl(Connection connection) {
		super(connection);
	}

	public Product findById(Long id) throws SQLException {//根据ID查询商品
		Product product = null;
		String sql = "select * from  easybuy_product  where ep_id=?";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setLong(1, id);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			product = createProductByResultSet(rs);
		}
		rs.close();
		prepareStatement.close();
		return product;
	}

	//根据分类查询本页显示的商品
	public List<Product> getProductsByCategoryLevelTwo(Long id, Pager pager)
			throws SQLException {
		List<Product> rtn = new ArrayList<Product>();
		String sql = "";
		if(id!=null){//按某类商品查找
			sql="select * from  easybuy_product where  EPC_CHILD_ID="+id+"  order by ep_id";
		}else{//查找全部商品
			sql = "select * from  easybuy_product order by ep_id";
		}
		
		if (pager != null)//分页显示	
			if(id!=null){//按某类商品查找
				sql = this.getSqlForPages("easybuy_product"," EPC_CHILD_ID="+id+" ","ep_id",
						" order by ep_id ","", pager); 
			}else{//查找全部商品
				sql = this.getSqlForPages("easybuy_product","ep_id"," order by ep_id ", pager); 
			}
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createProductByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	//根据分类查询商品
	public List<Product> getProductsByCategoryLevelOne(Long id)
			throws SQLException {
		List<Product> rtn = new ArrayList<Product>();
		String sql = "select * from  easybuy_product ";
		if (id != null)
			sql = sql + " where epc_id = " + id;
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createProductByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	//由结果集生成对象
	private Product createProductByResultSet(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getLong("ep_id"));
		product.setName(rs.getString("ep_name"));
		product.setDescription(rs.getString("ep_description"));
		product.setPrice(rs.getFloat("ep_price"));
		product.setStock(rs.getLong("ep_stock"));
		product.setCategoryId(rs.getLong("epc_id"));
		product.setChildCategoryId(rs.getLong("epc_child_id"));
		product.setFileName(rs.getString("ep_file_name"));
		return product;
	}

	//更新某款商品的分类
	public void modifyCategoryOfProductBySql(String sql) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.executeUpdate();
		ps.close();
	}

	//删除一款商品
	public void delete(Long id) throws SQLException {
		String sql = " DELETE FROM easybuy_product where ep_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		ps.close();
	}

	public Long save(Product product) throws SQLException {
		//Long id = getAutoGenerateId();
		String sql = " INSERT INTO easybuy_product(ep_name,ep_description,ep_price,"
				+ "ep_stock,epc_id,epc_child_id,ep_file_name) values(?,?,?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, product.getName());
		ps.setString(2, product.getDescription());
		ps.setFloat(3, product.getPrice());
		ps.setLong(4, product.getStock());
		ps.setLong(5, product.getCategoryId());
		if (product.getChildCategoryId() == null)
			ps.setNull(6, Types.BIGINT);
		else
			ps.setLong(6, product.getChildCategoryId());
		if (product.getFileName() == null)
			ps.setNull(7, Types.VARCHAR);
		else
			ps.setString(7, product.getFileName());
		ps.executeUpdate();		
		//查找当前自增长的产品ID
		sql="select @@IDENTITY ";
		ps = connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		//利用当前自增长的产品ID给商品图片命名
		if(rs.next()){
			product.setId(rs.getLong(1));
			sql = " update  easybuy_product set ep_file_name='"+rs.getLong(1)+product.getFileName()+"'where ep_id="+rs.getLong(1);
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();	
		}			
		ps.close();
		return rs.getLong(1);
	}

	public void update(Product product) throws SQLException {
		String sql = " UPDATE easybuy_product SET ep_name=?,ep_description=?,ep_price=?,"
				+ "ep_stock=?,epc_id=?,epc_child_id=?,ep_file_name=? where ep_id=?";
		if(product.getFileName() == null ||product.getFileName().equals("")){
			sql = " UPDATE easybuy_product SET ep_name=?,ep_description=?,ep_price=?,"
					+ "ep_stock=?,epc_id=?,epc_child_id=? where ep_id=?";
		}
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, product.getName());
		ps.setString(2, product.getDescription());
		ps.setFloat(3, product.getPrice());
		ps.setLong(4, product.getStock());
		ps.setLong(5, product.getCategoryId());
		if (product.getChildCategoryId() == null)
			ps.setNull(6, Types.BIGINT);
		else
			ps.setLong(6, product.getChildCategoryId());
		if (product.getFileName() != null && !product.getFileName().equals("")){
			ps.setString(7, product.getId()+product.getFileName());			
			ps.setLong(8, product.getId());
		}else{
			ps.setLong(7, product.getId());
		}
			
		ps.executeUpdate();
		ps.close();
	}

	public long getProductRowCountByCategoryLevelTwo(Long categoryId)
			throws SQLException {
		if (categoryId == null)
			return getRowCount("easybuy_product");
		else
			return getRowCount("easybuy_product", " epc_child_id=" + categoryId);
	}

	public void updateStock(Long id, long quantity) throws SQLException {
		String sql = " UPDATE easybuy_product SET ep_stock=ep_stock - ? where ep_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, quantity);
		ps.setLong(2, id);
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public List<Product> getProductsByCategoryLevelTwo(Long id, Pager pager,
			String condition) throws SQLException {//带条件查询
		List<Product> rtn = new ArrayList<Product>();
		String sql = "";
		if(id!=null){//按某类商品查找
			sql="select * from  easybuy_product where  "+"EPC_CHILD_ID="+id+"  order by ep_id";
			if(!condition.equals(""))
				sql="select * from  easybuy_product where  "+condition+"and EPC_CHILD_ID="+id+"  order by ep_id";
		}else{//查找全部商品
			sql = "select * from  easybuy_product "+" order by ep_id";
			if(!condition.equals(""))
				sql = "select * from  easybuy_product where"+condition+" order by ep_id";
		}		
		if (pager != null)//分页显示	
			if(id!=null){//按某类商品查找
				sql = this.getSqlForPages("easybuy_product"," EPC_CHILD_ID="+id+" ","ep_id",
						" order by ep_id ","", pager); 				
				if(!condition.equals(""))
					sql = this.getSqlForPages("easybuy_product",condition+" and EPC_CHILD_ID="+id+" ","ep_id",
							" order by ep_id ","", pager); 					
			}else{//查找全部商品					
				sql = this.getSqlForPages("easybuy_product","ep_id"," order by ep_id ", pager); 
				if(!condition.equals(""))
					sql = this.getSqlForPages("easybuy_product",condition,"ep_id"," order by ep_id ", "",pager); 
			}	
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createProductByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	@Override
	public long getProductRowCountByCategoryLevelTwo(Long categoryId,
			String condition) throws SQLException {
		if (categoryId == null){			
			if(!condition.equals(""))
				return getRowCount("easybuy_product",condition);
			return getRowCount("easybuy_product");
		}else{
			if(!condition.equals(""))
				return getRowCount("easybuy_product", condition+" and epc_child_id=" + categoryId);
			return getRowCount("easybuy_product", " epc_child_id=" + categoryId);
		}
			
	}

	@Override
	public void updateChildID(int parentid) throws SQLException {
		String sql = "update  EASYBUY_PRODUCT set EPC_CHILD_ID=null,EPC_ID=null where EPC_CHILD_ID in (select EPC_ID from EASYBUY_PRODUCT_CATEGORY where EPC_ID=? or EPC_PARENT_ID=?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, parentid);
		ps.setInt(2, parentid);
		ps.executeUpdate();
		ps.close();		
	}

}
