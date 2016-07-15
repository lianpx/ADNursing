package com.ad.bean;

import java.io.Serializable;
import java.util.Date;


// 帖子的评论
public class Comment implements Serializable{

	private Integer comment_id; // 评论的id，数据库的唯一标识
	private String comment_text;// 评论的内容
	private Date comment_date;// 评论的时间
	private Integer post_id;// 评论所对应的帖子的id
	private Integer owner_id;// 评论所拥有的用户的id
	private String img_url;// 评论附上的图片
	
	public Comment()
	{
		super();
	}
	
	// 上传评论只能填写内容和附上图片
	public Comment(String comment_text, String img_url)
	{
		super();
		this.comment_text = comment_text;
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

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}
	
	public String getImg_url(){
		return img_url;
	}
	
	public void setImg_url(String img_url){
		this.img_url = img_url;
	}

}

