package com.ad.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.ad.business.CommentBean;
import com.ad.business.PostBean;
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
		String op = request.getParameter("op");
		Integer userId = -1;
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
				

		String owner = request.getParameter("owner");
		userId = service.getUserId(owner);
		
		//System.out.println((int)userId);	
		List<CommentBean> comments = service.getCommentsByOwner(userId);
		
		
		if ("android".equals(mode)) {
			JSONArray jsonArray = new JSONArray(comments);
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
