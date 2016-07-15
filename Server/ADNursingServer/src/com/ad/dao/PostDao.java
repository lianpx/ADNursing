package com.ad.dao;

import java.util.List;

import com.ad.bean.Post;

/**
 * 跟数据库post交接的接口
 */
public interface PostDao {
	/**
	 * 查找所有帖子
	 * @param
	 * @return 所有帖子的列表
	 */
	List<Post> findAllPost();
	
	/**
	 * 查找用户的帖子
	 * @param userId 用户Id
	 * @return 用户的所有帖子的列表
	 */
	List<Post> findPostByOwner(Integer user_id);
	
	/**
	 * 查找某个种类的帖子
	 * @param post_kind 帖子分类
	 * @return 某个种类的帖子列表
	 */
	List<Post> findPostByKind(String post_kind);
	
	/**
	 * 帖子的评论数加1
	 * @param postId 帖子Id
	 * @return 
	 */
	void incrPostComment(int postId);
	
	/**
	 * 帖子的评论数减1
	 * @param postId 帖子Id
	 * @return 
	 */
	void descrPostComment(int postId);
	
	/**
	 * 保存帖子
	 * @param post 一条帖子
	 * @return 
	 */
	int save(Post post);
	
	/**
	 * 删除帖子
	 * @param userId 用户Id，postId 帖子Id
	 * @return 
	 */
	void del(Integer userId, Integer postId);
	
	/**
	 * 查找某个帖子
	 * @param postId 帖子Id
	 * @return 一条帖子
	 */
	Post get(int postId);
	
	void updatePostImgUrl(Integer post_id, String imgUrl);
	
	
}
