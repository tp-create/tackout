package cn.htzb.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.dao.NewsDao;
import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.News;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.util.Validator;

public class NewsDaoImpl extends BaseDaoImpl implements NewsDao {

	public NewsDaoImpl(Connection connection) {
		super(connection);
	}

	public void delete(Long id) throws SQLException {//删除新闻
		String sql = " DELETE FROM easybuy_news where en_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		ps.close();
	}

	public void save(News news) throws SQLException {//保存新闻
		//Long id = getAutoGenerateId();
		String sql = " INSERT INTO easybuy_news(en_title,en_content,"
				+ "en_create_time) values(?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setDate(3, Validator.toSqlDate(news.getCreateTime()));
		ps.executeUpdate();
		//查找当前自增长的新闻ID
		sql="select @@IDENTITY ";
		ps = connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();	
		if(rs.next()){
			news.setId(rs.getLong(1));
		}		
		ps.close();	
	}

	public void update(News news) throws SQLException {//更新新闻
		String sql = " UPDATE easybuy_news set en_title=?,en_content=?,"
				+ "en_create_time=? where en_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setDate(3, Validator.toSqlDate(news.getCreateTime()));
		ps.setLong(4, news.getId());
		ps.executeUpdate();
		ps.close();
	}

	public News findById(long id) throws SQLException {//根据ID获取新闻
		News news = null;
		String sql = "select * from  easybuy_news  where en_id=?";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setLong(1, id);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			news = createNewsByResultSet(rs);
		}
		rs.close();
		prepareStatement.close();
		return news;
	}

	public List<News> getAllNews(Pager pager) throws SQLException {//获取本页要显示的新闻
		List<News> rtn = new ArrayList<News>();
		String sql="select * from  easybuy_news  order by en_create_time desc";
		if (pager != null){
			sql = this.getSqlForPages("easybuy_news","en_id",
					" order by en_create_time desc", pager);
		}	
		System.out.println(sql);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createNewsByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	private News createNewsByResultSet(ResultSet rs) throws SQLException {//由结果集创建对象
		News news = new News();
		news.setId(rs.getLong("en_id"));
		news.setTitle(rs.getString("en_title"));
		news.setContent(rs.getString("en_content"));
		news.setCreateTime(rs.getDate("en_create_time"));
		return news;
	}

	public long getNewsRowCount()  throws SQLException {//获取新闻共有多少条记录
		return getRowCount("easybuy_news");
	}

}
