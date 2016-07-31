package com.ad.dao;

import java.util.List;

import com.ad.bean.FavoritesPost;
import com.ad.bean.Post;

/**
 * 跟数据库favorites_post交接的接口
 */
public interface FavoritesPostDao {
	
	/**
	 * 查找用户的收藏
	 * @param  userId 用户ID
	 * @return 所有收藏帖子的列表
	 */
	List<Post> findFavoritesPostByOwner(Integer userId);
	
	/**
	 * 收藏帖子
	 * @param favoritesPost，有对应的userId和postId
	 * @return 
	 */
	int save(FavoritesPost favoritesPost);
	
	/**
	 * 删除收藏帖子
	 * @param userId 用户Id，postId 帖子Id
	 * @return 
	 */
	void del(Integer userId, Integer postId);
	
	int delByUserIdAndPostId(Integer userId, Integer postId);
	
	/**
	 * 是否已被收藏
	 * @param userId 用户Id，postId 帖子Id
	 * @return 1为已被收藏，0为未被收藏
	 */
	int isFavorite(Integer userId, Integer postId);
}
