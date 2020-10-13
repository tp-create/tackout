package cn.htzb.easybuy.dao;

import java.sql.SQLException;
import java.util.List;

import cn.htzb.easybuy.entity.News;
import cn.htzb.easybuy.entity.Pager;

public interface NewsDao {

	void delete(Long id) throws SQLException;//删除新闻

	void update(News news) throws SQLException;//更新新闻

	void save(News news) throws SQLException;//保存新闻

	List<News> getAllNews(Pager pager) throws SQLException;//获取本页要显示的新闻

	News findById(long id) throws SQLException;//根据ID获取新闻

	long getNewsRowCount()  throws SQLException ;//获取新闻共有多少条记录

}
