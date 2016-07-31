package com.ad.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ad.bean.Comment;
import com.ad.bean.FavoritesPost;
import com.ad.bean.Post;
import com.ad.bean.Test;
import com.ad.bean.User;
import com.ad.business.CommentBean;
import com.ad.business.PostBean;
import com.ad.business.TestBean;
import com.ad.business.UserBean;
import com.ad.dao.CommentDao;
import com.ad.dao.TestDao;
import com.ad.dao.UserDao;
import com.ad.dao.impl.UserDaoImpl;
import com.ad.exception.ADException;
import com.ad.service.BussinessService;
import com.ad.dao.PostDao;
import com.ad.dao.impl.PostDaoImpl;
import com.ad.dao.impl.CommentDaoImpl;
import com.ad.dao.impl.FavoritesPostDaoImpl;
import com.ad.dao.impl.TestDaoImpl;
import com.ad.dao.FavoritesPostDao;



public class BussinessServiceImpl implements BussinessService{

	private UserDao userDao = new UserDaoImpl();
	
	public int validLogin(String username, String pass) throws ADException {
		User user = userDao.findUserByNameAndPass(username, pass);
		if (user != null) {
			return user.getUser_id();
		}
		return -1;
	}

	public int register(User user) throws ADException {
		int userId = userDao.findUserIdByName(user.getUsername());
		if (userId < 0) { //用户名不存在，可以注册
			return userDao.addUser(user);
		}
		return -1;
	}

	public String getOwner(int ownerId) throws ADException {
		try {
			User user = userDao.get(ownerId);
			if(user!=null){
				return user.getUsername();				
			}
			return null;
		} catch (Exception e) {
			throw new ADException("根据用户id获取用户名称出现异常,请重试");
		}
	}
	
	@Override
	public Integer getUserId(String username) throws ADException {
		try {
			Integer userId = userDao.findUserIdByName(username);
			return userId; 
		} catch (Exception e) {
			throw new ADException("根据用户id获取用户名称出现异常,请重试");
		}
	}

	@Override
	public UserBean getUser(Integer userId) throws ADException {
		try {
			User user = userDao.get(userId);
			UserBean ib = new UserBean();
			initUser(ib, user);
			return ib;
		} catch (Exception e) {
			throw new ADException("获取用户信息出现异常,请重试");
		}
	}
	
	@Override
	public void updateUser(UserBean user) throws ADException {
		try {
			userDao.updateUser(user);
		} catch (Exception e) {
			throw new ADException("更新用户信息出现异常,请重试");
		}
		
	}
	
	@Override
	public int updateTestedPerson(UserBean user) throws ADException {
		try {
			int res = userDao.updateTestedPerson(user);
			return res;
		} catch (Exception e) {
			throw new ADException("更新用户信息出现异常,请重试");
		}
		
	}
	
	@Override
	public int updateUserInfor(int userId, String gender, String address,
			String description) {
		try {
			int res = userDao.updateUserInfor(userId, gender, address, description);
			return res;
		} catch (Exception e) {
			throw new ADException("更新用户信息出现异常,请重试");
		}
	}

	@Override
	public int updateUserImgUrl(int userId, String url) {
		try {
			int res = userDao.updateUserImgUrl(userId, url);
			return res;
			
		} catch (Exception e) {
			throw new ADException("更新用户信息出现异常,请重试");
		}
	}
	
	private void initUser(UserBean ib, User user) {
		ib.setUser_id(user.getUser_id());
		ib.setUsername(user.getUsername());
		ib.setImg_url(user.getImg_url());
		ib.setAddress(user.getAddress());
		ib.setGender(user.getGender());
		ib.setDescription(user.getDescription());
		ib.setTestedPerson(user.getTested_person());
		ib.setTestedGender(user.getTested_gender());
		ib.setTestedBirth(user.getTested_birth());
	}

	private PostDao postDao = new PostDaoImpl();

	@Override
	public List<PostBean> getPosts() throws ADException {
		try {
			List<Post> posts = postDao.findAllPost();
			List<PostBean> result = new ArrayList<PostBean>();
			for (Iterator<Post> it = posts.iterator(); it.hasNext();) {
				PostBean ib = new PostBean();
				initPost(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			throw new ADException("查询文章出现异常,请重试");
		}
	}


	@Override
	public List<PostBean> getPostsByOwner(Integer userId)
			throws ADException {
		try {
			List<PostBean> result = new ArrayList<PostBean>();
			List<Post> posts = postDao.findPostByOwner(userId);
			for(Iterator<Post> it = posts.iterator();it.hasNext();){
				PostBean ib = new PostBean();
				initPost(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询用户所有的文章出现异常，请重试");
		}
	}
	
	@Override
	public List<PostBean> getPostsByKind(String post_kind) throws ADException {
		try {
			List<PostBean> result = new ArrayList<PostBean>();
			List<Post> posts = postDao.findPostByKind(post_kind);
			for(Iterator<Post> it = posts.iterator();it.hasNext();){
				PostBean ib = new PostBean();
				initPost(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询类型的文章出现异常，请重试");
		}
	}


	@Override
	public PostBean getPost(int postId) throws ADException {
		try {
			Post post = postDao.get(postId);
			PostBean ib = new PostBean();
			initPost(ib, post);
			return ib;
		} catch (Exception e) {
			throw new ADException("根据物品id获取文章详细信息出现异常,请重试");
		}
	}


	@Override
	public int addPost(Post post, Integer userId) throws ADException {
		//设置资源属性
		post.setComment_num(0);
		post.setPost_date(new Date());
		post.setOwner_id(userId);
		//保存post对象
		int postId = postDao.save(post);
		return postId;
	}


	@Override
	public int delPostByUserIdAndPostId(Integer userId, Integer postId)
			throws ADException {
		int res = postDao.del(userId, postId);
		return res;
		
	}


	@Override
	public void incrPostThx(int postId) {
		try {
			postDao.incrPostComment(postId);
		} catch (Exception e) {
			throw new ADException("增加评论数出现异常,请重试");
		}
	}
	
	@Override
	public void descrPostThx(int postId) {
		try {
			postDao.descrPostComment(postId);
		} catch (Exception e) {
			throw new ADException("增加评论数出现异常,请重试");
		}
		
	}
	
	@Override
	public int updatePostImgUrl(int postId, String imgUrl) {
		try {
			int res = postDao.updatePostImgUrl(postId, imgUrl);
			return res;
		} catch (Exception e) {
			throw new ADException("出现异常,请重试");
		}
	}
	
	private void initPost(PostBean ib, Post post) {
		ib.setPost_id(post.getPost_id());
		ib.setPost_title(post.getPost_title());
		ib.setPost_text(post.getPost_text());
		ib.setPost_kind(post.getPost_kind());
		ib.setPost_date(post.getPost_date());
		ib.setComment_num(post.getComment_num());
		if (post.getOwner_id() != null)
			ib.setOwner(getOwner(post.getOwner_id()));
		ib.setImg_url(post.getImg_url());
	}


	
	private CommentDao commentDao = new CommentDaoImpl();
	
	
	@Override
	public List<CommentBean> getComments() throws ADException {
		try {
			List<Comment> comments  = commentDao.findAllComments();
			List<CommentBean> result = new ArrayList<CommentBean>();
			for (Iterator<Comment> it = comments.iterator(); it.hasNext();) {
				CommentBean ib = new CommentBean();
				initComment(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			throw new ADException("查询文章出现异常,请重试");
		}
	}


	@Override
	public List<CommentBean> getCommentsByOwner(Integer userId)
			throws ADException {
		try {
			List<CommentBean> result = new ArrayList<CommentBean>();
			List<Comment> comments = commentDao.findCommentByOwner(userId);
			for(Iterator<Comment> it = comments.iterator();it.hasNext();){
				CommentBean ib = new CommentBean();
				initComment(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询用户所有的评论出现异常，请重试");
		}
	}
	
	@Override
	public List<CommentBean> getCommentsByPost(Integer postId)
			throws ADException {
		try {
			List<CommentBean> result = new ArrayList<CommentBean>();
			List<Comment> comments = commentDao.findCommentByPost(postId);
			for(Iterator<Comment> it = comments.iterator();it.hasNext();){
				CommentBean ib = new CommentBean();
				initComment(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询文章所有的评论出现异常，请重试");
		}
	}


	@Override
	public CommentBean getComment(int commentId) throws ADException {
		try {
			Comment comment = commentDao.get(commentId);
			CommentBean ib = new CommentBean();
			initComment(ib, comment);
			return ib;
		} catch (Exception e) {
			throw new ADException("根据物品id获取文章详细信息出现异常,请重试");
		}
	}


	@Override
	public int addComment(Comment comment, Integer userId, Integer postID)
			throws ADException {
		comment.setComment_date(new Date());
		comment.setOwner_id(userId);
		comment.setPost_id(postID);

	    int res = commentDao.save(comment);

		return res;
	}


	@Override
	public int delCommentByUserIdAndCommentId(Integer userId, Integer commentId)
			throws ADException {
		int res = commentDao.del(userId, commentId);
		return res;
	}
	
	@Override
	public int updateCommentImgUrl(int commentId, String imgUrl) {
		try {
			int res = commentDao.updateCommentImgUrl(commentId, imgUrl);
			return res;
		} catch (Exception e) {
			throw new ADException("出现异常,请重试");
		}
		
	}
	

	
	private void initComment(CommentBean ib, Comment comment) {
		ib.setComment_id(comment.getComment_id());
		ib.setComment_text(comment.getComment_text());
		ib.setComment_date(comment.getComment_date());
		if (comment.getOwner_id() != null)
			ib.setOwner(getOwner(comment.getOwner_id()));
		ib.setPost_id(comment.getPost_id());
		ib.setImg_url(comment.getImg_url());
	}

	private FavoritesPostDao favoritesPostDao = new FavoritesPostDaoImpl();
	
	@Override
	public List<PostBean> getFavoritesPostByOwner(Integer userId) {
		List<PostBean> result = new ArrayList<PostBean>();
		try {
			List<Post> posts = favoritesPostDao.findFavoritesPostByOwner(userId);
			for(Iterator<Post> it = posts.iterator();it.hasNext();){
				PostBean ib = new PostBean();
				initPost(ib,it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ADException("根据用户id获取收藏文章出现异常,请重试");
		}
	}


	@Override
	public int addFavoritesPost(FavoritesPost favoritesPost)
			throws ADException {
		return favoritesPostDao.save(favoritesPost);
		
	}


	@Override
	public void delFavorite(Integer userId, Integer postId) {
		favoritesPostDao.del(userId, postId);
		
	}
	
	@Override
	public int delFavoriteByUserIdAndPostId(Integer userId, Integer postId){
		return favoritesPostDao.delByUserIdAndPostId(userId, postId);
	}
	
	@Override
	public int isFavorite(Integer userId, Integer postId) {
		return favoritesPostDao.isFavorite(userId, postId);
	}


	private TestDao testDao = new TestDaoImpl();
	
	@Override
	public int addTestResult(Test test, Integer useId) {
		test.setOwner_id(useId);
		test.setTest_date(new Date());
	
		int res = testDao.save(test);

		return res;
	}


	@Override
	public List<TestBean> getTestsByOwner(Integer useId) {
		try {
			List<TestBean> result = new ArrayList<TestBean>();
			List<Test> tests = testDao.findTestByOwner(useId);
			for(Iterator<Test> it = tests.iterator();it.hasNext();){
				TestBean ib = new TestBean();
				initTest(ib, it.next());
				result.add(ib);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询用户所有的测试出现异常，请重试");
		}
	}
	
	private void initTest(TestBean ib, Test test) {
		ib.setTestId(test.getTest_id());
		ib.setTestDate(test.getTest_date());
		ib.setTestType(test.getTest_type());
		ib.setTestPoint(test.getTest_point());
		if(test.getOwner_id() != null) {
			ib.setOwner(getOwner(test.getOwner_id()));
		}
	}
















}

