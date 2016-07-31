package com.ad.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.UserBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 更新用户测试者信息的接口
 */

@WebServlet(urlPatterns = "/android/updateTestInfor.jsp")
public class UpdateTestInforServlet extends HttpServlet {
	BussinessService service = new BussinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		//response.setContentType("text/html");
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		String testedPerson = request.getParameter("testedPerson");
		String testedBirth = request.getParameter("testedBirth");
		String testedGender = request.getParameter("testedGender");
			
		UserBean userBean =  service.getUser(userId);
		userBean.setTestedPerson(testedPerson);
		userBean.setTestedBirth(testedBirth);
		userBean.setTestedGender(testedGender);
		
		userId = service.updateTestedPerson(userBean);
		
		if("android".equals(mode)){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("userId", userId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.getWriter().println(jsonObject.toString());
		}else if("web".equals(mode)){			
//				response.getWriter().write("更新成功。2秒后自动转向添加页面");
//				response.setHeader("Refresh", "2;URL=" + request.getContextPath()
//						+ "/servlet/ViewOwnerPostServlet?mode=web&op=owner");
			request.getRequestDispatcher("/manage/index.jsp").forward(
					request, response);	
		}
		
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
