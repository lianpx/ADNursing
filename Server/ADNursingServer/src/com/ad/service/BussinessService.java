package com.ad.service;

import java.util.List;

import com.ad.bean.Comment;
import com.ad.bean.FavoritesPost;
import com.ad.bean.Post;
import com.ad.bean.Test;
import com.ad.bean.User;
import com.ad.business.CommentBean;
import com.ad.business.PostBean;
import com.ad.business.TestBean;
import com.ad.business.UserBean;
import com.ad.exception.ADException;;

public interface BussinessService {
	
	/**********用户相关接口***********/
	
	/**
	 * 根据用户名，密码验证登录是否成功
	 * @param username 登录的用户名
 	 * @param pass 登录的密码
	 * @return 登录成功返回用户ID，否则返回-1
	 */
	int validLogin(String username , String pass)
		throws ADException;

	/**
	 * 根据用户提供信息注册账号
	 * @param user 用户提供信息
	 * @return 注册成功返回用户ID，否则返回-1
	 */
	int register(User user)
			throws ADException;
	
	/**
	 * 根据用户名找userId
	 * @param username 用户名 
	 * @return userId  -1为没有，其他为userId
	 */
	Integer getUserId(String username) throws ADException;
	
	/**
	 * 获取用户信息
	 * @param userId 用户id
	 * @return 用户信息
	 */
	UserBean getUser(Integer userId) throws ADException;
	
	/**
	 * 更新用户信息
	 * @param user 一个用户的信息
	 * @return 
	 */
	void updateUser(UserBean user) throws ADException;
	
	/**
	 * 更新用户测试者信息
	 * @param user 一个用户的信息
	 * @return 
	 */
	int updateTestedPerson(UserBean user) throws ADException;
	
	
	int updateUserInfor(int userId, String gender, String address, String description);
	
	int updateUserImgUrl(int userId, String url);
	
	
	/**********帖子相关接口***********/
	
	/**
	 * 查询全部帖子
	 * @return 全部帖子
	 */
	List<PostBean> getPosts()throws ADException;

	/**
	 * 根据用户查找帖子
	 * @param userId 所属者的ID
	 * @return 属于当前用户的帖子
	 */
	List<PostBean> getPostsByOwner(Integer userId)
		throws ADException;
	
	/**
	 * 查找某个种类的帖子
	 * @param post_kind 帖子分类
	 * @return 某个种类的帖子列表
	 */
	List<PostBean> getPostsByKind(String post_kind)
			throws ADException;

	
	/**
	 * 根据帖子id，获取帖子
	 * @param itemId 帖子id;
	 * @return 指定id对应的帖子
	 */
	PostBean getPost(int postId) throws ADException;
	
	/**
	 * 添加帖子
	 * @param post 新增的帖子
	 * @param userId 添加者的ID
	 * @return 新增帖子的主键
	 */
	int addPost(Post post, Integer userId)
		throws ADException;
	
	/**
	 * 删除帖子
	 * @param userId 拥有者ID
	 * @param postId 帖子ID
	 */
	int delPostByUserIdAndPostId(Integer userId, Integer postId)
		throws ADException;
	
	/**
	 * 增加帖子的评论
	 * @param postId 帖子id;
	 * @return void
	 */
	void incrPostThx(int postId);
	
	
	/**
	 * 帖子的评论数减1
	 * @param postId 帖子Id
	 * @return 
	 */
	void descrPostThx(int postId);
	
	int updatePostImgUrl(int postId, String imgUrl);
	
	
	/**********评论相关接口***********/

	/**
	 * 查找所有的评论
	 * @param
	 * @return 所有评论的列表
	 */
	List<CommentBean> getComments()throws ADException;

	/**
	 * 查找用户拥有的所有评论
	 * @param use_id 用户id
	 * @return 用户所有评论的列表
	 */
	List<CommentBean> getCommentsByOwner(Integer userId)
		throws ADException;
	
	/**
	 * 查找帖子拥有的所有评论
	 * @param post_id 帖子id
	 * @return 帖子所有评论的列表
	 */
	List<CommentBean> getCommentsByPost(Integer postId)
			throws ADException;

	
	/**
	 * 查找某条评论
	 * @param commnentId 评论id
	 * @return 一条评论
	 */
	CommentBean getComment(int commentId) throws ADException;
	
	/**
	 * 添加评论
	 * @param comment 一条评论
	 * @param userId 添加者的Id
	 * @param postID 评论的帖子的id
	 * @return
	 */
	int addComment(Comment comment, Integer userId, Integer postID)
		throws ADException;
	
	/**
	 * 删除评论
	 * @param userId 用户id  commentId 评论id
	 * @return 
	 */
	int delCommentByUserIdAndCommentId(Integer userId, Integer commentId)
		throws ADException;
	
	int updateCommentImgUrl(int commentId, String imgUrl);
	
	
	/**********收藏相关接口***********/
	
	/**
	 * 查找用户的收藏
	 * @param  userId 用户ID
	 * @return 所有收藏帖子的列表
	 */
	List<PostBean> getFavoritesPostByOwner(Integer userId);
	
	/**
	 * 收藏帖子
	 * @param favoritesPost，有对应的userId和postId
	 * @return 
	 */
	int addFavoritesPost(FavoritesPost favoritesPost)
		throws ADException;
	
	/**
	 * 删除收藏帖子
	 * @param userId 用户Id，postId 帖子Id
	 * @return 
	 */
	void delFavorite(Integer useId, Integer postId);
	
	int delFavoriteByUserIdAndPostId(Integer userId, Integer postId);
	/**
	 * 是否已被收藏
	 * @param userId 用户Id，postId 帖子Id
	 * @return 1为已被收藏，0为未被收藏
	 */
	int isFavorite(Integer useId, Integer postId);
	
	
	/**********测试相关接口***********/
	/**
	 * 保存测试结果
	 * @param test 一条测试结果
	 * @param userId 被测试的人的账号的id
	 * @return 
	 */
	int addTestResult(Test test, Integer useId);
	
	/**
	 * 查找用户的测试者的所有测试结果
	 * @param user_id 用户Id
	 * @return 该用户的测试者的所有测试结果的列表
	 */
	List<TestBean> getTestsByOwner(Integer useId);
	
	
}
