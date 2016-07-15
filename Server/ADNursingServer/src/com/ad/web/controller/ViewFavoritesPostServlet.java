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
 * 查看用户收藏的帖子的接口
 */

@WebServlet(urlPatterns = "/android/ViewFavoritesPost.jsp")
public class ViewFavoritesPostServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求参数，这个参数标记的是返回的json数据，用于提供给客户端
		String mode = request.getParameter("mode");

		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		
		// 获取userId
		String owner = request.getParameter("owner");
		Integer userId = service.getUserId(owner);	
		
		// 查询所有收藏文章
		List<PostBean> posts = service.getFavoritesPostByOwner(userId);
		
		if("android".equals(mode)){
			//返回json数据提供给客户端
			JSONArray jsonArray = new JSONArray(posts);
			response.getWriter().println(jsonArray.toString());

		}else if("web".equals(mode)){
			//将数据保存在request域中，显示在服务端界面上
			request.setAttribute("posts", posts);
			request.getRequestDispatcher("/manage/viewFavoritesPost.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

