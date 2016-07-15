package com.ad.business;

import java.util.Date;

// 向客户端传递的评论信息
public class CommentBean {
	private Integer comment_id;// 评论id
	private String comment_text;// 评论内容
	private Date comment_date;// 评论日期
	private Integer post_id;// 评论所属帖子的id
	private String owner;// 评论所属用户的用户名
	private String img_url;// 评论的图片
	
	public CommentBean()
	{
	}
	
	public CommentBean(Integer comment_id, String comment_text, Date comment_date,
			Integer post_id, String owner, String img_url)
	{
		this.comment_id = comment_id;
		this.comment_text = comment_text;
		this.comment_date = comment_date;
		this.post_id = post_id;
		this.owner = owner;
		this.img_url = img_url;
	}

	public Integer getComment_id() {
		return comment_id;
	}

	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment_text() {
		return comment_text;
	}

	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}

	public Date getComment_date() {
		return comment_date;
	}

	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}

	public Integer getPost_id() {
		return post_id;
	}

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
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
