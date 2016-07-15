package com.ad.business;

// 向客户端传递的收藏帖子信息
public class FavoritesPostBean {
	private Integer user_id;// 收藏帖子的所属用户的id
	private Integer post_id;// 收藏帖子的帖子的id

	public FavoritesPostBean()
	{
	}
	public FavoritesPostBean(Integer user_id, Integer post_id)
	{
		this.user_id = user_id;
		this.post_id = post_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getPost_id() {
		return post_id;
	}
	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}
}
