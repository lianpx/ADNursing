package com.ad.bean;

import java.io.Serializable;
import java.util.Date;

// 用户
public class User implements Serializable {

	private Integer user_id;// 用户id，数据库唯一标识
	private String username;// 用户名，唯一
	private String userpass;// 用户密码
	private String img_url;// 用户头像
	private String gender;// 用户性别
	private String address;// 用户地址
	private String description;// 用户描述
	private String tested_person;// 被用户测试的人的名字
	private String tested_birth;// 被用户测试的人的出生日期
	private String tested_gender;// 被用户测试的人的性别
	
	public User(){
		super();
	}
	
	public User(Integer user_id, String username, String userpass,
			String img_url, String gender, String address, String description,
			String tested_person, String tested_birth, String tested_gender){
		super();
		this.user_id = user_id;
		this.username = username;
		this.userpass = userpass;
		this.img_url = img_url;
		this.gender = gender;
		this.address = address;
		this.description = description;
		this.tested_person = tested_person;
		this.tested_gender = tested_gender;
		this.tested_birth = tested_birth;
		
	}
	
	public User(String username, String userpass, String img_url,
			String gender, String address, String description,
			String tested_person, String tested_birth, String tested_gender){
		super();
		this.username = username;
		this.userpass = userpass;
		this.img_url = img_url;
		this.gender = gender;
		this.address = address;
		this.description = description;
		this.tested_person = tested_person;
		this.tested_gender = tested_gender;
		this.tested_birth = tested_birth;
	}
	
	// 注册用户时需要用户名和密码，其他默认为空
	public User(String username, String userpass){
		super();
		this.username = username;
		this.userpass = userpass;
		this.img_url = "";
		this.gender = "";
		this.address = "";
		this.description = "";
		this.tested_person = "";
		this.tested_gender = "";
		this.tested_birth = "";
	}
	
	// 更新用户个人信息的时候，需要头像图片，性别，地址和描述信息
	public User(String img_url, String gender, 
			String address, String description){
		super();
		this.img_url = img_url;
		this.gender = gender;
		this.address = address;
		this.description = description;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User [user_id=" + user_id + ", username=" + username
				+ ", userpass=" + userpass + "]";
	}
	
	public Integer getUser_id(){
		return user_id;
	}
	
	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUserpass(){
		return userpass;
	}
	
	public void setUserpass(String userpass){
		this.userpass = userpass;
	}
	
	public String getImg_url(){
		return img_url;
	}
	
	public void setImg_url(String img_url){
		this.img_url = img_url;
	}
	
	public String getGender(){
		return gender;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getTested_person(){
		return tested_person;
	}
	
	public void setTested_person(String tested_person){
		this.tested_person = tested_person;	
	}
	
	public String getTested_gender(){
		return tested_gender;
	}
	
	public void setTested_gender(String tested_gender){
		this.tested_gender = tested_gender;
	}
	
	public String getTested_birth(){
		return tested_birth;
	}
	
	public void setTested_birth(String tested_birth){
		this.tested_birth = tested_birth;
	}
	

}
