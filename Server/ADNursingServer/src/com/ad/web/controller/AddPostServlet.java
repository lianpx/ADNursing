package com.ad.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ad.bean.Post;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

import java.util.UUID;

/**
 * 添加帖子的接口
 */

@WebServlet(urlPatterns = "/android/addPost.jsp")
public class AddPostServlet extends HttpServlet {
	// 获取业务逻辑对象
	BussinessService service = new BussinessServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");

		String postTitle = request.getParameter("postTitle");
		String postText = request.getParameter("postText");
		String postKind = request.getParameter("postKind");
		
		
		String owner = request.getParameter("owner");
		Post post = new Post(postTitle, postText, postKind, "");

		Integer userId = Integer.parseInt(request.getParameter("userId"));
		int postId = service.addPost(post, userId);
			
		if("android".equals(mode)){
			
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("postId", postId);
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
