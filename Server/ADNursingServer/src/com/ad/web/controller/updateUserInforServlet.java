package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.bean.Comment;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

public class updateUserInforServlet extends HttpServlet {
	// 获取业务逻辑对象
			BussinessService service = new BussinessServiceImpl();

			public void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				String mode = request.getParameter("mode");

				String gender = request.getParameter("gender");
				String address = request.getParameter("address");
				String description = request.getParameter("description");

				int userId = Integer.parseInt(request.getParameter("userId"));



				userId = service.updateUserInfor(userId, gender, address, description);
							
				if("android".equals(mode)){
					
					JSONObject jsonObject = new JSONObject();
					try {
						jsonObject.put("userId", userId);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					response.getWriter().println(jsonObject.toString());
				}
			}
			
			public void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				doGet(request, response);
			}
}
