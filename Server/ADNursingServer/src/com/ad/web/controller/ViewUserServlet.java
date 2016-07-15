package com.ad.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;

import com.ad.bean.User;
import com.ad.business.UserBean;
import com.ad.business.PostBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 查看用户信息的接口
 */

@WebServlet(urlPatterns = "/android/viewUser.jsp")
public class ViewUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取请求参数，这个参数标记的是返回的json数据，用于提供给客户端
		String mode = request.getParameter("mode");
		String op = request.getParameter("op");
		Integer userId = -1;
		
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		
		String owner = request.getParameter("owner");
		userId = Integer.parseInt(request.getParameter("userId"));

		/* 获取用户的信息 */
		UserBean user = service.getUser(userId);
		if("android".equals(mode)){
			//返回json数据提供给客户端
			JSONObject jsonUser = new JSONObject(user);	
			response.getWriter().println(jsonUser.toString());
		}else if("web".equals(mode)){
			//将数据保存在request域中，显示在服务端界面上
			request.setAttribute("user", user);
			request.getRequestDispatcher("/manage/viewMe.jsp").forward(
					request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
