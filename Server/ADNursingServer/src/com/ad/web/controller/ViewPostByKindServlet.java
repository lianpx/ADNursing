package com.ad.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.PostBean;
import com.ad.business.UserBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 根据种类和时间顺序查看帖子的接口
 */

@WebServlet(urlPatterns = "/android/viewPostByKind.jsp")
public class ViewPostByKindServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String op = request.getParameter("op");
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		String post_kind = request.getParameter("postKind");
		

		List<PostBean> tmp_posts = service.getPostsByKind(post_kind);
		
		List<PostBean> posts = new ArrayList<PostBean>();
		
		for(int i = tmp_posts.size()-1; i >= 0; i--) {
			posts.add(tmp_posts.get(i));
		}
		
		
		
		JSONArray jsonArray = new JSONArray(posts);
		
		for(int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String owner="";
			try {
				owner = jsonObject.getString("owner");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int userId = service.getUserId(owner);
			UserBean userBean = service.getUser(userId);
			String userImgUrl = userBean.getImg_url();
			try {
				jsonArray.getJSONObject(i).put("userImgUrl", userImgUrl);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		if ("android".equals(mode)) {
			
			response.getWriter().println(jsonArray.toString());
		} else if ("web".equals(mode)) {
			request.setAttribute("posts", posts);
			request.getRequestDispatcher("/manage/viewPostByKind.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
