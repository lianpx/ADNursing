package com.ad.bean;

import java.io.Serializable;
import java.util.Date;


// 帖子
public class Post implements Serializable{
	
	private Integer post_id;// 帖子的id
	private String post_title;// 帖子的标题
	private String post_text;// 帖子的内容
	private String post_kind;// 帖子的类型
	private Date post_date;// 帖子的日期
	private Integer comment_num;// 评论人数
	private Integer owner_id;// 拥有帖子的用户
	private String img_url;// 帖子附图片


	public Post()
	{
		super();
	}
		
	// 上传帖子需要标题，内容，分类和图片（可以为空）
	public Post(String post_title, String post_text, String post_kind,String img_url)
	{
		super();
		this.post_title = post_title;
		this.post_text = post_text;
		this.post_kind = post_kind;
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
