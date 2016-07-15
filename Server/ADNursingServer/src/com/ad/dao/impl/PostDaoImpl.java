package com.ad.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.ad.bean.Post;
import com.ad.dao.PostDao;
import com.ad.util.DBCPUtil;
import com.ad.bean.User;
import com.ad.business.UserBean;

public class PostDaoImpl implements PostDao{

	QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	/**
	 * 查找所有帖子
	 * @param
	 * @return 所有帖子的列表
	 */
	@Override
	public List<Post> findAllPost() {
		try {
			String sql = "select * from post";
			List<Post> posts = qr.query(sql, new BeanListHandler<Post>(
					Post.class));
			return posts;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找用户的帖子
	 * @param userId 用户Id
	 * @return 用户的所有帖子的列表
	 */
	@Override
	public List<Post> findPostByOwner(Integer user_id) {
		try {
			String sql = "select * from post where owner_id=?";
			List<Post> posts = qr.query(sql, new BeanListHandler<Post>(
					Post.class), user_id);
			// 设置帖子拥有者
			for (Post post : posts) {
				// 设置拥有者
				String ownerSql = "select * from user where user_id=any(select owner_id from post where owner_id=?)";
				User owner = qr.query(ownerSql, new BeanHandler<User>(
						User.class), user_id);
				post.setOwner_id(owner.getUser_id());
			}
			return posts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 帖子的评论数加1
	 * @param postId 帖子Id
	 * @return 
	 */
	@Override
	public void incrPostComment(int postId) {
		try {
			String sql = "update post set comment_num=comment_num+1 where post_id=?";
			qr.update(sql, postId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 帖子的评论数减1
	 * @param postId 帖子Id
	 * @return 
	 */
	@Override
	public void descrPostComment(int postId) {
		try {
			String sql = "update post set comment_num=comment_num-1 where post_id=?";
			qr.update(sql, postId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 保存帖子
	 * @param post 一条帖子
	 * @return 
	 */
	@Override
	public int save(Post post) {
		try {
			String sql = "insert into post(post_title,post_text,post_kind,post_date,comment_num,owner_id,img_url) "
					+ //
					"values(?,?,?,?,?,?,?)";
			qr.update(sql, post.getPost_title(), post.getPost_text(), post.getPost_kind(),
					post.getPost_date(), post.getComment_num(),
					post.getOwner_id(),post.getImg_url());
			String sql2 = "select @@identity as post_id";
			Object post_id = qr.query(sql2, new ScalarHandler(1));
			post.setPost_id(Integer.valueOf(post_id.toString()));
			return Integer.valueOf(post_id.toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除帖子
	 * @param userId 用户Id，postId 帖子Id
	 * @return 
	 */
	@Override
	public void del(Integer userId, Integer postId) {
		try {
			String sql = "delete from post where post_id=? and owner_id=?";
			qr.update(sql, postId, userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
	}
	
	
	/**
	 * 查找某个帖子
	 * @param postId 帖子Id
	 * @return 一条帖子
	 */
	@Override
	public Post get(int postId) {
		try {
			String sql = "select * from post where post_id=?";
			Post post = qr
					.query(sql, new BeanHandler<Post>(Post.class), postId);
			// 设置拥有者
			String ownerSql = "select * from user where user_id = any(select owner_id from post where post_id=?)";
			User owner = qr.query(ownerSql, new BeanHandler<User>(User.class),
					post.getPost_id());
			post.setOwner_id(owner.getUser_id());
			return post;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找某个种类的帖子
	 * @param post_kind 帖子分类
	 * @return 某个种类的帖子列表
	 */
	@Override
	public List<Post> findPostByKind(String post_kind) {
		try {
			String sql = "select * from post where post_kind=?";
			List<Post> posts = qr.query(sql, new BeanListHandler<Post>(
					Post.class), post_kind);
			// 设置帖子拥有者
			for (Post post : posts) {
				// 设置拥有者
				String ownerSql = "select * from user where user_id=any(select owner_id from post where post_id=?)";
				User owner = qr.query(ownerSql, new BeanHandler<User>(
						User.class), post.getPost_id());
				post.setOwner_id(owner.getUser_id());
			}
			return posts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updatePostImgUrl(Integer post_id, String imgUrl) {
		try {
			String sql = "update post set  img_url=? where post_id=?";
			qr.update(sql, imgUrl, post_id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



}
