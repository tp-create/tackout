package cn.htzb.easybuy.dao;

import java.sql.SQLException;
import java.util.List;

import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.Pager;

public interface CommentDao {

	//获取当页留言,isUser来确定用户是何种身份，身份不一样，排序也不一样
	List<Comment> getComments(Pager pager, boolean isUser) throws SQLException;

	void save(Comment comment) throws SQLException;//保存留言

	Comment findById(Long id) throws SQLException;//根据ID获取留言

	long getCommentRowCount() throws SQLException;//获取留言共有多少条记录

	void update(Comment comment) throws SQLException;//更新留言

	void delete(Long id)  throws SQLException;//删除留言

}
