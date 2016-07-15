package com.ad.dao;

import com.ad.bean.User;
import com.ad.business.UserBean;

public interface UserDao {
	/**
	 * 查找某个用户
	 * @param username 用户名      pass 密码
	 * @return 一个用户
	 */
	User findUserByNameAndPass(String username, String pass);
	
	/**
	 * 根据用户名找userId
	 * @param username 用户名 
	 * @return userId  -1为没有，其他为userId
	 */
	Integer findUserIdByName(String username);
	
	/**
	 * 查找某个用户
	 * @param userId 用户Id
	 * @return 一个用户
	 */
    User get(Integer userId);
	
    /**
	 * 添加用户
	 * @param user 一个用户
	 * @return -1为注册失败（用户名已被使用） >0为注册成功
	 */
	int addUser(User user);
	
	/**
	 * 更新用户信息
	 * @param user 一个用户的信息
	 * @return 
	 */
	void updateUser(UserBean user);
	
	/**
	 * 更新用户测试者信息
	 * @param user 一个用户的信息
	 * @return 
	 */
	void updateTestedPerson(UserBean user);
	
	int updateUserInfor(int userId, String gender, String address, String description);
	
	int updateUserImgUrl(int userId, String url);
}
