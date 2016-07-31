package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.bean.FavoritesPost;
import com.ad.bean.Test;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 添加测试结果的接口
 */

@WebServlet(urlPatterns = "/android/addTest.jsp")
public class AddTestServlet extends HttpServlet {
	
	BussinessService service = new BussinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String op = request.getParameter("op");
		// 获取userId
		int userId = Integer.parseInt(request.getParameter("userId"));

		
		Integer resultId = -1;			
		
		
		int testPoint = Integer.parseInt(request.getParameter("testPoint"));
		
		Test test = new Test("1", testPoint); 
		int testId = service.addTestResult(test, userId);
		
		if("android".equals(mode)){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("testId", testId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.getWriter().println(jsonObject.toString());
		}else if("web".equals(mode)){
//			response.getWriter().write("收藏成功。2秒后自动转向收藏页面");
//			response.setHeader("Refresh", "2;URL=" + request.getContextPath()
//					+ "/servlet/ViewOwnerFavoritesServlet?mode=web");
			request.getRequestDispatcher("/manage/index.jsp").forward(
					request, response);	
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
