package com.ad.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.PostBean;
import com.ad.business.UserBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 查看各个类别的帖子的数目的接口
 */

@WebServlet(urlPatterns = "/android/viewKindNum.jsp")
public class ViewKindNumServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");

		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		

		List<PostBean> posts1 = service.getPostsByKind("病症疑问");
		List<PostBean> posts2 = service.getPostsByKind("名医推荐");
		List<PostBean> posts3 = service.getPostsByKind("药物推荐");
		List<PostBean> posts4 = service.getPostsByKind("交流分享");
						
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("BZYW", posts1.size());
			jsonObject.put("MYTJ", posts2.size());
			jsonObject.put("YWTJ", posts3.size());
			jsonObject.put("JLFX", posts4.size());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if ("android".equals(mode)) {
			
			response.getWriter().println(jsonObject.toString());
		} else if ("web".equals(mode)) {

			request.getRequestDispatcher("/manage/viewPostByKind.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
