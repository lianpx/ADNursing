package com.ad.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.CommentBean;
import com.ad.business.PostBean;
import com.ad.business.UserBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 查看用户的评论的接口
 */

@WebServlet(urlPatterns = "/android/viewOwnerComment.jsp")
public class ViewOwnerCommentServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");

		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
				

		int userId = Integer.parseInt(request.getParameter("userId"));
		
		List<CommentBean> comments = service.getCommentsByOwner(userId);
		
		JSONArray jsonArray = new JSONArray(comments);
		
		for(int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int postId = 0;
			try {
				postId = Integer.parseInt(jsonObject.getString("post_id"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PostBean postBean = service.getPost(postId);
			String postImgUrl = postBean.getImg_url();
			String postTitle = postBean.getPost_title();
			try {
				jsonArray.getJSONObject(i).put("postImgUrl", postImgUrl);
				jsonArray.getJSONObject(i).put("postTitle", postTitle);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if ("android".equals(mode)) {
			response.getWriter().println(jsonArray.toString());
		} else if ("web".equals(mode)) {
			request.setAttribute("comments", comments);
			request.getRequestDispatcher("/manage/viewOwnerComment.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
