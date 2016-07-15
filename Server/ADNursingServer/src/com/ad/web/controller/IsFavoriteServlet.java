package com.ad.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.CommentBean;
import com.ad.business.PostBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 判断是否已被收藏的接口
 */

@WebServlet(urlPatterns = "/android/isFavorite.jsp")
public class IsFavoriteServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		//String op = request.getParameter("op");
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		
		String owner = request.getParameter("owner");
		Integer userId = service.getUserId(owner);		
		Integer postId = Integer.parseInt(request.getParameter("postId"));
		
		Integer result = service.isFavorite(userId, postId);
				
		if ("android".equals(mode)) {
			try {
				JSONObject jsonObject = new JSONObject();
				//把验证的userId封装成JSONObject
				jsonObject.put("result", result);
				//输出响应
				response.getWriter().println(jsonObject.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if ("web".equals(mode)) {	
			request.getRequestDispatcher("/manage/viewOwnerPost.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
