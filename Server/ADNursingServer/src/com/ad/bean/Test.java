package com.ad.bean;

import java.io.Serializable;
import java.util.Date;

// 测试结果
public class Test implements Serializable{
	
	private Integer test_id; // 测试结果的id，数据库唯一标识
	private Integer owner_id;// 测试结果所属用户的id
	private String test_type;// 测试的类型
	private Integer test_point;// 测试的分数
	private Date test_date;// 测试的日期
	
	public Test()
	{
		super();
	}
		
	// 上传测试结果需要分数和类型信息
	public Test(String test_type, Integer test_point)
	{
		super();
		this.test_type = test_type;
		this.test_point = test_point;
	}
	
	public Integer getTest_id(){
		return test_id;
	}
	
	public void setTest_id(Integer test_id){
		this.test_id = test_id;
	}
	
	public Integer getOwner_id(){
		return owner_id;
	}
	
	public void setOwner_id(Integer owner_id){
		this.owner_id = owner_id;
	}
	
	public String getTest_type(){
		return test_type;
	}
	
	public void setTest_type(String test_type){
		this.test_type = test_type;
	}
	
	public Integer getTest_point(){
		return test_point;
	}
	
	public void setTest_point(Integer test_point){
		this.test_point = test_point;
	}
	
	public Date getTest_date(){
		return test_date;
	}
	
	public void setTest_date(Date test_date){
		this.test_date = test_date;
	}
	
	
}
