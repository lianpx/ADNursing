package com.ad.bean;

import java.io.Serializable;

// 收藏的帖子
public class FavoritesPost implements Serializable{
	
	private Integer user_id;// 收藏该帖子的用户的id
	private Integer post_id;// 收藏帖子的id
	
	public FavoritesPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// 收藏帖子时，需要确定用户和帖子
	public FavoritesPost(Integer user_id, Integer post_id) {
		super();
		this.user_id = user_id;
		this.post_id = post_id;
	}
	
	@Override
	public String toString() {
		return "FavoritesPost [user_id=" + user_id + ", post_id=" + post_id + "]";
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
