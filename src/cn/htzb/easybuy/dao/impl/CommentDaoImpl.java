package cn.htzb.easybuy.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.htzb.easybuy.dao.CommentDao;
import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.util.Validator;

public class CommentDaoImpl extends BaseDaoImpl implements CommentDao {

	public CommentDaoImpl(Connection connection) {
		super(connection);
	}

	//获取当页留言,isUser来确定用户是何种身份，身份不一样，排序也不一样
	public List<Comment> getComments(Pager pager,boolean isUser) throws SQLException {
		List<Comment> rtn = new ArrayList<Comment>();
		String sql = "select * from  easybuy_comment  order by ec_create_time desc";
		//分页显示
		if (pager != null && !isUser){//管理员查看时未回复，和创建最早的留言在最前
			sql = this.getSqlForPages("easybuy_comment","ec_id",
					" order by EC_REPLY_TIME asc", " order by ec_create_time desc",pager);
		}else if(pager != null && isUser){//普通用户查看时创建最晚的留言在最前
			sql = this.getSqlForPages("easybuy_comment","ec_id",
					" order by ec_create_time desc", " order by ec_create_time desc",pager);
		}		
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			rtn.add(createCommentByResultSet(rs));
		}
		rs.close();
		prepareStatement.close();
		return rtn;
	}

	//由结果集创建对象
	private Comment createCommentByResultSet(ResultSet rs) throws SQLException {
		Comment comment = new Comment();
		comment.setId(rs.getLong("ec_id"));
		comment.setReply(rs.getString("ec_reply"));
		comment.setContent(rs.getString("ec_content"));
		comment.setNickName(rs.getString("ec_nick_name"));
		comment.setCreateTime(rs.getDate("ec_create_time"));
		comment.setReplyTime(rs.getDate("ec_reply_time"));
		return comment;
	}

	public void save(Comment comment) throws SQLException {//保存留言
		String sql = " INSERT INTO easybuy_comment(ec_reply,ec_content,ec_nick_name,"
				+ "ec_reply_time,ec_create_time) values(?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, comment.getReply());
		ps.setString(2, comment.getContent());
		ps.setString(3, comment.getNickName());
		ps.setDate(4, Validator.toSqlDate(comment.getReplyTime()));
		ps.setDate(5, Validator.toSqlDate(comment.getCreateTime()));
		ps.executeUpdate();
		//查找当前自增长的评论ID
		sql="select @@IDENTITY ";
		ps = connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();	
		if(rs.next()){
			comment.setId(rs.getLong(1));
		}		
		ps.close();	
	}

	public Comment findById(Long id) throws SQLException {//根据ID获取留言
		Comment comment = null;
		String sql = "select * from  easybuy_comment  where ec_id=?";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setLong(1, id);
		ResultSet rs = prepareStatement.executeQuery();
		while (rs.next()) {
			comment = createCommentByResultSet(rs);
		}
		rs.close();
		prepareStatement.close();
		return comment;
	}

	public long getCommentRowCount() throws SQLException {//获取留言共有多少条记录
		return getRowCount("easybuy_comment");
	}

	public void update(Comment comment) throws SQLException {//更新留言
		String sql = " UPDATE easybuy_comment set ec_reply=?,"
				+ "ec_reply_time=? where ec_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, comment.getReply());
		ps.setDate(2, Validator.toSqlDate(comment.getReplyTime()));
		ps.setLong(3, comment.getId());
		ps.executeUpdate();
		ps.close();
	}

	public void delete(Long id) throws SQLException {//删除留言
		String sql = " DELETE FROM easybuy_comment where ec_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		ps.close();
	}

}
