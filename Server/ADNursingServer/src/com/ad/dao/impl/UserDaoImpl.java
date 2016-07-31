package com.ad.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.ad.bean.User;
import com.ad.business.UserBean;
import com.ad.dao.UserDao;
import com.ad.util.DBCPUtil;

public class UserDaoImpl implements UserDao{

	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	/**
	 * 查找某个用户
	 * @param username 用户名      pass 密码
	 * @return 一个用户
	 */
	@Override
	public User findUserByNameAndPass(String username, String pass) {
		String sql = "select * from user where username=? and userpass=?";
		try {
			List<User> userList = qr.query(sql, new BeanListHandler<User>(User.class), username, pass);
			if (userList.size() == 1)
			{
				return (User)userList.get(0);
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据用户名找userId
	 * @param username 用户名 
	 * @return userId  -1为没有，其他为userId
	 */
	@Override
	public Integer findUserIdByName(String username) {
		String sql = "select * from user where username=?";
		try {
			List<User> userList = qr.query(sql, new BeanListHandler<User>(User.class), username);
			if (userList.size() == 1)
			{
				return (Integer)userList.get(0).getUser_id();
			}
			return -1;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找某个用户
	 * @param userId 用户Id
	 * @return 一个用户
	 */
	@Override
	public User get(Integer userId) {
		try {
			String sql = "select * from user where user_id=?";
			User user = qr.query(sql, new BeanHandler<User>(User.class), userId);			
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加用户
	 * @param user 一个用户
	 * @return -1为注册失败（用户名已被使用） >0为注册成功
	 */
	@Override
	public int addUser(User user) {
		try {
			String sql = "insert into user(username,userpass,img_url,gender,address,description,tested_person,tested_birth,tested_gender) "
					+ //
					"values(?,?,?,?,?,?,?,?,?)";
			qr.update(sql, user.getUsername(), user.getUserpass(),user.getImg_url(),
					user.getGender(),user.getAddress(),user.getDescription(),
					user.getTested_person(),user.getTested_birth(),user.getTested_gender());
			String sql2 = "select @@identity as user_id";
			Object user_id = qr.query(sql2, new ScalarHandler(1));
			user.setUser_id(Integer.valueOf(user_id.toString()));
			return user.getUser_id();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}

	/**
	 * 更新用户信息
	 * @param user 一个用户的信息
	 * @return 
	 */
	@Override
	public void updateUser(UserBean user) {
		try {
			String sql = "update user set  img_url=?,gender=?,address=?,description=? where user_id=?";
			qr.update(sql, user.getImg_url(),user.getGender(),
					user.getAddress(),user.getDescription(),user.getUser_id());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 更新用户测试者信息
	 * @param user 一个用户的信息
	 * @return 
	 */
	@Override
	public int updateTestedPerson(UserBean user) {
		try {
			String sql = "update user set tested_person=?,tested_birth=?,tested_gender=? where user_id=?";
			int res = qr.update(sql,user.getTestedPerson(),user.getTestedBirth(),
					user.getTestedGender(),user.getUser_id());
			if(res > 0) {
				return user.getUser_id();
			}
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateUserInfor(int userId, String gender, String address, String description) {
		try {
			String sql = "update user set gender=?,address=?,description=? where user_id=?";
			int res = qr.update(sql,gender,address,description,userId);
			if(res > 0) {
				return userId;
			}
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateUserImgUrl(int userId, String url) {
		try {
		String sql = "update user set img_url=? where user_id=?";
		int res = qr.update(sql,url,userId);
		if(res > 0){
			return userId;
		}
		return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
