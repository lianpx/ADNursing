package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ad.bean.FavoritesPost;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 添加收藏的接口
 */

@WebServlet(urlPatterns = "/android/addFavorites.jsp")
public class AddFavoritesServlet extends HttpServlet {
	// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();

		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String mode = request.getParameter("mode");
			String op = request.getParameter("op");
			// 获取userId
			String owner = request.getParameter("owner");
			Integer userId = service.getUserId(owner);
			Integer postId = Integer.parseInt(request.getParameter("postId"));
			
			Integer resultId = -1;			
			FavoritesPost favoritesPost = new FavoritesPost(userId, postId);
			resultId = service.addFavoritesPost(favoritesPost);
			
			if("android".equals(mode)){
				// 添加成功
				if (resultId > 0) {
					response.getWriter().println("恭喜您，收藏成功!");
				} else {
					response.getWriter().println("对不起，收藏失败!");
				}
			}else if("web".equals(mode)){
//				response.getWriter().write("收藏成功。2秒后自动转向收藏页面");
//				response.setHeader("Refresh", "2;URL=" + request.getContextPath()
//						+ "/servlet/ViewOwnerFavoritesServlet?mode=web");
				request.getRequestDispatcher("/manage/index.jsp").forward(
						request, response);	
			}
			
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}
}

