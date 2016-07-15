package com.ad.business;

// 向客户端传递的测试结果信息
import java.util.Date;

public class TestBean {
	private Integer test_id;// 测试结果id
	private String owner;// 测试账号的用户名
	private String test_type;// 测试的类型 
	private Integer test_point;// 测试的分数
	private Date test_date;// 测试的时间
	
	public TestBean()
	{
	}
		
	public TestBean(Integer test_id, String owner, String test_type, Integer test_point, Date test_date)
	{
		this.test_id = test_id;
		this.owner = owner;
		this.test_type = test_type;
		this.test_point = test_point;
		this.test_date = test_date;
	}
	
	public Integer getTestId(){
		return test_id;
	}
	
	public void setTestId(Integer test_id){
		this.test_id = test_id;
	}
	
	public String getOwner(){
		return owner;
	}
	
	public void setOwner(String owner){
		this.owner = owner;
	}
	
	public String getTestType(){
		return test_type;
	}
	
	public void setTestType(String test_type){
		this.test_type = test_type;
	}
	
	public Integer getTestPoint(){
		return test_point;
	}
	
	public void setTestPoint(Integer test_point){
		this.test_point = test_point;
	}
	
	public Date getTestDate(){
		return test_date;
	}
	
	public void setTestDate(Date test_date){
		this.test_date = test_date;
	}
	
	
}
