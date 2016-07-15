package com.ad.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.ad.bean.Comment;
import com.ad.dao.CommentDao;
import com.ad.util.DBCPUtil;
import com.ad.bean.Post;
import com.ad.bean.User;

public class CommentDaoImpl implements CommentDao{

	QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
		
	/**
	 * 查找所有的评论
	 * @param
	 * @return 所有评论的列表
	 */
	@Override
	public List<Comment> findAllComments() {
		try {
			String sql = "select * from comment";
			List<Comment> comments = qr.query(sql, new BeanListHandler<Comment>(
					Comment.class));
			return comments;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找用户拥有的所有评论
	 * @param use_id 用户id
	 * @return 用户所有评论的列表
	 */
	@Override
	public List<Comment> findCommentByOwner(Integer user_id) {
		try {
			String sql = "select * from comment where owner_id=?";
			List<Comment> comments = qr.query(sql, new BeanListHandler<Comment>(
					Comment.class), user_id);
			
			// 设置评论拥有者，对应帖子
			for (Comment comment : comments) {
				// 设置拥有者
				String ownerSql = "select * from user where user_id=any(select owner_id from comment where comment_id=?)";
				User owner = qr.query(ownerSql, new BeanHandler<User>(
						User.class), comment.getComment_id());
				comment.setOwner_id(owner.getUser_id());
				
				// 设置对应帖子
				String postSql = "select * from post where post_id=any(select post_id from comment where comment_id=?)";
				Post post = qr.query(postSql, new BeanHandler<Post>(
						Post.class), comment.getComment_id());
				comment.setPost_id(post.getPost_id());
			}

			return comments;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找文章拥有的所有评论
	 * @param post_id 帖子id
	 * @return 文章所有评论的列表
	 */
	@Override
	public List<Comment> findCommentByPost(Integer post_id) {
		try {
			String sql = "select * from comment where post_id=?";
			List<Comment> comments = qr.query(sql, new BeanListHandler<Comment>(
					Comment.class), post_id);
			// 设置评论拥有者
			for (Comment comment : comments) {
				// 设置拥有者
				String ownerSql = "select * from user where user_id=any(select owner_id from comment where post_id=? and comment_id=?)";
				User owner = qr.query(ownerSql, new BeanHandler<User>(
						User.class), post_id,comment.getComment_id());
				comment.setOwner_id(owner.getUser_id());
			}
			return comments;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 保存评论
	 * @param comment 一条评论
	 * @return
	 */
	@Override
	public int save(Comment comment) {
		try {
			String sql = "insert into comment(comment_text,comment_date,post_id,owner_id,img_url) "
					+ //
					"values(?,?,?,?,?)";
			qr.update(sql, comment.getComment_text(), comment.getComment_date(), 
					comment.getPost_id(), comment.getOwner_id(),comment.getImg_url());
			String sql2 = "select @@identity as comment_id";
			Object comment_id = qr.query(sql2, new ScalarHandler(1));
			comment.setComment_id(Integer.valueOf(comment_id.toString()));
			return Integer.valueOf(comment_id.toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 删除评论
	 * @param userId 用户id  commentId 评论id
	 * @return 
	 */
	@Override
	public void del(Integer userId, Integer commentId) {
		try {
			String sql = "delete from comment where comment_id=? and owner_id=?";
			qr.update(sql, commentId, userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 查找某条评论
	 * @param commnentId 评论id
	 * @return 一条评论
	 */
	@Override
	public Comment get(int commnentId) {
		try {
			String sql = "select * from comment where comment_id=?";
			Comment comment = qr
					.query(sql, new BeanHandler<Comment>(Comment.class), commnentId);
			// 设置拥有者
			String ownerSql = "select * from user where user_id = any(select owner_id from comment where comment_id=?)";
			User owner = qr.query(ownerSql, new BeanHandler<User>(User.class),
					comment.getComment_id());
			comment.setOwner_id(owner.getUser_id());
			
			// 设置对应帖子
			String postSql = "select * from post where post_id=any(select post_id from comment where comment_id=?)";
			Post post = qr.query(postSql, new BeanHandler<Post>(
					Post.class), comment.getComment_id());
			comment.setPost_id(post.getPost_id());
			return comment;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateCommentImgUrl(Integer comment_id, String imgUrl) {
		try {
			String sql = "update comment set img_url=? where comment_id=?";
			qr.update(sql, imgUrl, comment_id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
