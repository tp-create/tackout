package cn.htzb.easybuy.biz.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.biz.FacilityService;
import cn.htzb.easybuy.dao.CommentDao;
import cn.htzb.easybuy.dao.NewsDao;
import cn.htzb.easybuy.dao.impl.CommentDaoImpl;
import cn.htzb.easybuy.dao.impl.NewsDaoImpl;
import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.News;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.UniqueConstraintException;
import cn.htzb.easybuy.util.DataSourceUtil;

public class FacilityServiceImpl implements FacilityService {

	public void deleteComment(String id) {//删除留言
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			CommentDao commentDao = new CommentDaoImpl(connection);
			commentDao.delete(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void deleteNews(String id) {//删除新闻
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			NewsDao newsDao = new NewsDaoImpl(connection);
			newsDao.delete(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public Comment findCommentById(String id) {//根据ID获取留言
		Connection connection = null;
		Comment comment = null;
		try {
			connection = DataSourceUtil.openConnection();
			CommentDao commentDao = new CommentDaoImpl(connection);
			comment = commentDao.findById(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return comment;
	}

	public News findNewsById(String id) {//根据ID获取新闻
		Connection connection = null;
		News news = null;
		try {
			connection = DataSourceUtil.openConnection();
			NewsDao newsDao = new NewsDaoImpl(connection);
			news = newsDao.findById(Long.parseLong(id));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return news;
	}

	public List<News> getAllNews(Pager pager) {//获取当页新闻
		Connection connection = null;
		List<News> rtn = new ArrayList<News>();
		try {
			connection = DataSourceUtil.openConnection();
			NewsDao newsDao = new NewsDaoImpl(connection);
			rtn = newsDao.getAllNews(pager);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	//获取当页留言,isUser来确定用户是何种身份，身份不一样，排序也不一样
	public List<Comment> getComments(Pager pager,boolean isUser) {
		Connection connection = null;
		List<Comment> rtn = new ArrayList<Comment>();
		try {
			connection = DataSourceUtil.openConnection();
			CommentDao commentDao = new CommentDaoImpl(connection);
			rtn = commentDao.getComments(pager, isUser);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public void saveComment(Comment comment) {//保存留言
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			CommentDao commentDao = new CommentDaoImpl(connection);
			commentDao.save(comment);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void saveNews(News news) {//保存新闻
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			NewsDao newsDao = new NewsDaoImpl(connection);
			newsDao.save(news);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23000")
					&& e.getMessage().contains("ORA-00001"))
				throw new UniqueConstraintException(e);
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void updateComment(Comment comment) {
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			CommentDao commentDao = new CommentDaoImpl(connection);
			commentDao.update(comment);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public void updateNews(News news) {//更新留言
		Connection connection = null;
		try {
			connection = DataSourceUtil.openConnection();
			NewsDao newsDao = new NewsDaoImpl(connection);
			newsDao.update(news);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23000")
					&& e.getMessage().contains("ORA-00001"))
				throw new UniqueConstraintException(e);
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
	}

	public long getCommentRowCount() {//获取留言共有多少条记录
		Connection connection = null;
		long rtn = 0;
		try {
			connection = DataSourceUtil.openConnection();
			CommentDao commentDao = new CommentDaoImpl(connection);
			rtn = commentDao.getCommentRowCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

	public long getNewsRowCount() {//获取新闻共有多少条记录
		Connection connection = null;
		long rtn = 0;
		try {
			connection = DataSourceUtil.openConnection();
			NewsDao newsDao = new NewsDaoImpl(connection);
			rtn = newsDao.getNewsRowCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.closeConnection(connection);
		}
		return rtn;
	}

}
