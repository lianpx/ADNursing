package com.ad.business;

import java.util.Date;

// 向客户端传递的帖子信息
public class PostBean {
	private Integer post_id;// 帖子id
	private String post_title;// 帖子标题
	private String post_text;// 帖子内容
	private String post_kind;// 帖子分类
	private Date post_date;// 帖子日期
	private Integer comment_num;// 评论人数
	private String owner;// 帖子所属用户的用户名
	private String img_url;// 帖子附带的图片


	public PostBean()
	{

	}
		
	public PostBean(Integer post_id, String post_title, String post_text,
			String post_kind, Date post_date, Integer comment_num, 
			String owner, String img_url)
	{
		this.post_id = post_id;
		this.post_title = post_title;
		this.post_text = post_text;
		this.post_kind = post_kind;
		this.post_date = post_date;
		this.comment_num = comment_num;
		this.owner = owner;
		this.img_url = img_url;
	}

	public Integer getPost_id() {
		return post_id;
	}

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_text() {
		return post_text;
	}

	public void setPost_text(String post_text) {
		this.post_text = post_text;
	}

	public String getPost_kind(){
		return post_kind;
	}
	
	public void setPost_kind(String post_kind){
		this.post_kind = post_kind;
	}
	
	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	public Integer getComment_num() {
		return comment_num;
	}

	public void setComment_num(Integer comment_num) {
		this.comment_num = comment_num;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getImg_url(){
		return img_url;
	}
	
	public void setImg_url(String img_url){
		this.img_url = img_url;
	}
}
