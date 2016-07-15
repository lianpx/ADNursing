package com.ad.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ad.bean.FavoritesPost;
import com.ad.bean.Post;
import com.ad.dao.FavoritesPostDao;
import com.ad.util.DBCPUtil;

public class FavoritesPostDaoImpl implements FavoritesPostDao{
	
	QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	/**
	 * 查找用户的收藏
	 * @param  userId 用户ID
	 * @return 所有收藏帖子的列表
	 */
	@Override
	public List<Post> findFavoritesPostByOwner(Integer userId) {
		try {
			String sql = "select * from post where post_id in" +
					"(select post_id from favorites_post where user_id=?)";
			List<Post> posts = qr.query(sql, new BeanListHandler<Post>(Post.class), userId);
			return posts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 收藏帖子
	 * @param favoritesPost，有对应的userId和postId
	 * @return 
	 */
	@Override
	public void save(FavoritesPost favoritesPost) {
		try {
			String sql = "insert into favorites_post (user_id,post_id) values(?,?)";
			qr.update(sql, favoritesPost.getUser_id(),favoritesPost.getPost_id());
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
			String sql = "delete from favorites_post where post_id=? and user_id=?";
			qr.update(sql, postId, userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
	}

	/**
	 * 是否已被收藏
	 * @param userId 用户Id，postId 帖子Id
	 * @return 1为已被收藏，0为未被收藏
	 */
	@Override
	public int isFavorite(Integer userId, Integer postId) {
		try {
			String sql = "select * from favorites_post where user_id=? and post_id=?";
			Post post = qr.query(sql, new BeanHandler<Post>(Post.class), userId, postId);
			if(post != null) {
				return 1;
			}
			return 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
