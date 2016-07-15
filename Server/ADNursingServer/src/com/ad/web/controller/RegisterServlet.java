package com.ad.web.controller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.bean.User;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 注册用户的接口
 */

@WebServlet(urlPatterns = "/android/register.jsp")
public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {															
		//获取模式请求参数。android：表示客户端请求地址，web表示后台请求地址
		String mode = request.getParameter("mode");
		
		String userName = request.getParameter("userName");
 		//String userName = new String(userName1.getBytes("ISO-8859-1"),"utf-8");	
		String userPassword = request.getParameter("userPassword");
		
 		// 获取业务逻辑对象
 		BussinessService service = new BussinessServiceImpl();
		User user = new User(userName, userPassword);
		
		Integer userId = 0;
		userId = service.register(user);
		if("android".equals(mode)){
			//userId>0表示注册成功
			try {
				JSONObject jsonObject = new JSONObject();
				//把验证的userId封装成JSONObject
				jsonObject.put("userId", userId);
				//输出响应
				response.getWriter().println(jsonObject.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if("web".equals(mode)){
			if (userId > 0) {
				//转发到首页
				request.getRequestDispatcher("/manage/index.jsp").forward(
						request, response);
			}
			else {
				//注册失败
				response.getWriter().write("注册失败");
				//返回错误信息！
				//request.getRequestDispatcher("/manage/register.jsp").forward(
				//		request, response);
				response.setHeader("Refresh", "2;URL=" + request.getContextPath()
						+ "/manage/register.jsp");
			}
		}
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
