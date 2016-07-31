package com.ad.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.ad.bean.Test;
import com.ad.bean.User;
import com.ad.dao.TestDao;
import com.ad.util.DBCPUtil;

public class TestDaoImpl implements TestDao{

	QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	/**
	 * 查找用户的测试者的所有测试结果
	 * @param user_id 用户Id
	 * @return 该用户的测试者的所有测试结果的列表
	 */
	@Override
	public List<Test> findTestByOwner(Integer user_id) {
		try {
			String sql = "select * from test where owner_id=?";
			List<Test> tests = qr.query(sql, new BeanListHandler<Test>(
					Test.class),user_id);
			System.out.println(tests.size());
			
			for (Test test : tests) {
				System.out.println(test.getTest_id());
			}
			 //设置物品拥有者
			for (Test test : tests) {
				// 设置拥有者
				String ownerSql = "select * from user where user_id=any(select owner_id from test where owner_id=?)";
				User owner = qr.query(ownerSql, new BeanHandler<User>(
						User.class), test.getOwner_id());
				test.setOwner_id(owner.getUser_id());
			}
			return tests;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 保存测试结果
	 * @param test 一条测试结果
	 * @return 
	 */
	@Override
	public int save(Test test) {
		try {
			String sql = "insert into test(owner_id,test_type,test_point,test_date) "
					+ //
					"values(?,?,?,?)";
			int res = qr.update(sql, test.getOwner_id(), test.getTest_type(),
					test.getTest_point(), test.getTest_date());
			String sql2 = "select @@identity as test_id";
			Object test_id = qr.query(sql2, new ScalarHandler(1));
			
			test.setTest_id(Integer.valueOf(test_id.toString()));
			if(res > 0) {
				return Integer.valueOf(test_id.toString());
			}
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 查找某条测试结果
	 * @param testId 测试结果Id
	 * @return 一条测试结果
	 */
	@Override
	public Test get(int testId) {
		try {
			String sql = "select * from test where test_id=?";
			Test test = qr
					.query(sql, new BeanHandler<Test>(Test.class), testId);
			// 设置拥有者
			String ownerSql = "select * from user where user_id = any(select owner_id from test where test_id=?)";
			User owner = qr.query(ownerSql, new BeanHandler<User>(User.class),
					test.getTest_id());
			test.setOwner_id(owner.getUser_id());
			
			return test;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
