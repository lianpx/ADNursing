package com.ad.dao;

import java.util.List;

import com.ad.bean.Comment;

/**
 * 跟数据库comment交接的接口
 */
public interface CommentDao {
	
	/**
	 * 查找所有的评论
	 * @param
	 * @return 所有评论的列表
	 */
	List<Comment> findAllComments();
	
	
	/**
	 * 查找用户拥有的所有评论
	 * @param use_id 用户id
	 * @return 用户所有评论的列表
	 */
	List<Comment> findCommentByOwner(Integer user_id);
	
	/**
	 * 查找帖子拥有的所有评论
	 * @param post_id 帖子id
	 * @return 帖子所有评论的列表
	 */
	List<Comment> findCommentByPost(Integer post_id);
	
	/**
	 * 保存评论
	 * @param comment 一条评论
	 * @return
	 */
	int save(Comment comment);
	
	/**
	 * 删除评论
	 * @param userId 用户id  commentId 评论id
	 * @return 
	 */
	int del(Integer userId, Integer commentId);
	
	/**
	 * 查找某条评论
	 * @param commnentId 评论id
	 * @return 一条评论
	 */
	Comment get(int commnentId);
	
	int updateCommentImgUrl(Integer comment_id, String imgUrl);
}
