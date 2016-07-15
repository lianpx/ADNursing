package com.ad.dao;

import java.util.List;

import com.ad.bean.Test;

public interface TestDao {

	/**
	 * 查找用户的测试者的所有测试结果
	 * @param user_id 用户Id
	 * @return 该用户的测试者的所有测试结果的列表
	 */
	List<Test> findTestByOwner(Integer user_id);
	
	/**
	 * 保存测试结果
	 * @param test 一条测试结果
	 * @return 
	 */
	void save(Test test);
	
	/**
	 * 查找某条测试结果
	 * @param testId 测试结果Id
	 * @return 一条测试结果
	 */
	Test get(int testId);
}
