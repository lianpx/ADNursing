package com.ad.business;

// 向客户端传递的用户信息
public class UserBean {
	private Integer user_id;// 用户id
	private String username;// 用户名
	private String img_url;// 用户头像
	private String gender;// 用户性别
	private String address;// 用户地址
	private String description;// 用户描述
	private String tested_person;// 测试者姓名
	private String tested_birth;// 测试者出生日期
	private String tested_gender;// 测试者性别
	
	public UserBean(){
		
	}
	
	public UserBean(Integer user_id, String username, String img_url,
			String gender, String address, String description,
			String tested_person, String tested_birth, String tested_gender){
		this.user_id = user_id;
		this.username = username;
		this.img_url = img_url;
		this.gender = gender;
		this.address = address;
		this.description = description;
		this.tested_person = tested_person;
		this.tested_gender = tested_gender;
		this.tested_birth = tested_birth;
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
	
	public String getTestedPerson(){
		return tested_person;
	}
	
	public void setTestedPerson(String tested_person){
		this.tested_person = tested_person;	
	}
	
	public String getTestedBirth(){
		return tested_birth;
	}
	
	public void setTestedBirth(String tested_birth){
		this.tested_birth = tested_birth;
	}
	
	public String getTestedGender(){
		return tested_gender;
	}
	
	public void setTestedGender(String tested_gender){
		this.tested_gender = tested_gender;
	}
}
