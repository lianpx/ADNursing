package com.ad.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.ad.business.PostBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 查看用户的收藏帖子的接口
 */

@WebServlet(urlPatterns = "/android/viewOwnerFavorites.jsp")
public class ViewOwnerFavoritesServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String op = request.getParameter("op");
		Integer userId = -1;
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		
		String owner = request.getParameter("owner");
		userId = service.getUserId(owner);	

		List<PostBean> posts = service.getFavoritesPostByOwner(userId);
		
		
		if ("android".equals(mode)) {
			JSONArray jsonArray = new JSONArray(posts);
			response.getWriter().println(jsonArray.toString());
		} else if ("web".equals(mode)) {
			request.setAttribute("posts", posts);
			request.getRequestDispatcher("/manage/viewOwnerFavorites.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
