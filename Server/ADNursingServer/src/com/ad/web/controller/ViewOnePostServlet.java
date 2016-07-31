package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.PostBean;
import com.ad.business.UserBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 查看某个帖子的接口
 */

@WebServlet(urlPatterns = "/android/viewOnePost.jsp")
public class ViewOnePostServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");

		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
				
		Integer postId = Integer.parseInt(request.getParameter("postId"));
		PostBean post = service.getPost(postId);
		String owner = post.getOwner();
		int userId = service.getUserId(owner);
		UserBean userBean = service.getUser(userId);
		String userImgUrl = userBean.getImg_url();
		
		if ("android".equals(mode)) {
			JSONObject jsonObject = new JSONObject(post);
			
			try {
				jsonObject.put("userImgUrl", userImgUrl);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().println(jsonObject.toString());
		} else if ("web".equals(mode)) {
			request.setAttribute("post", post);		
			request.getRequestDispatcher("/manage/viewOnePost.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
