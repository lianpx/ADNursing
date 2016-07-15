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

import com.ad.business.PostBean;
import com.ad.business.TestBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 查看用户测试者的测试结果的接口
 */

@WebServlet(urlPatterns = "/android/viewOwnerTest.jsp")
public class ViewOwnerTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		//String op = request.getParameter("op");
		Integer userId = -1;
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
				

		String owner = request.getParameter("owner");

		
		userId = service.getUserId(owner);
		
		userId = Integer.parseInt(request.getParameter("userId"));
		
//		System.out.println("userId"+userId);
		
		List<TestBean> tmp_tests = service.getTestsByOwner(userId);
		List<TestBean> tests = new ArrayList<TestBean>();
		
		for(int i = tmp_tests.size()-1; i >= 0; i--) {
			tests.add(tmp_tests.get(i));
		}
		

		if ("android".equals(mode)) {
			JSONArray jsonArray = new JSONArray(tests);
			response.getWriter().println(jsonArray.toString());
		} else if ("web".equals(mode)) {
			request.setAttribute("posts", tests);
			request.getRequestDispatcher("/manage/viewOwnerPost.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
