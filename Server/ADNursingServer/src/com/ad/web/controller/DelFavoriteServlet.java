package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ad.business.CommentBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 删除收藏的接口
 */

@WebServlet(urlPatterns = "/android/delFavorite.jsp")
public class DelFavoriteServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		//String op = request.getParameter("op");
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		
		String owner = request.getParameter("owner");
		Integer userId = service.getUserId(owner);		
		Integer postId = Integer.parseInt(request.getParameter("postId"));
		
		service.delFavorite(userId, postId);
		
		
		if ("android".equals(mode)) {
			response.getWriter().println("删除收藏");
		} else if ("web".equals(mode)) {	
			request.getRequestDispatcher("/manage/viewOwnerFavorites.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
