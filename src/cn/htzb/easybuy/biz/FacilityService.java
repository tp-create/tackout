package cn.htzb.easybuy.biz;

import java.util.List;

import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.News;
import cn.htzb.easybuy.entity.Pager;

public interface FacilityService {

	void deleteComment(String parameter);//删除留言

	//获取当页留言,isUser来确定用户是何种身份，身份不一样，排序也不一样
	List<Comment> getComments(Pager pager, boolean isUser);

	void saveComment(Comment comment);//保存留言

	Comment findCommentById(String parameter);//根据ID获取留言

	void updateComment(Comment comment);//更新留言

	void saveNews(News news);//保存新闻

	News findNewsById(String parameter);//根据ID获取新闻

	void updateNews(News news);//更新新闻

	List<News> getAllNews(Pager pager);//获取当页新闻

	void deleteNews(String parameter);//删除新闻

	long getCommentRowCount();//获取留言共有多少条记录

	long getNewsRowCount();//获取新闻共有多少条记录

}
