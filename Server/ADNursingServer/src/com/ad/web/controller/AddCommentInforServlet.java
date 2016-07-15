package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.bean.Comment;
import com.ad.bean.Post;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

@WebServlet(urlPatterns = "/android/addCommentInfor.jsp")
public class AddCommentInforServlet extends HttpServlet {
	// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();

		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String mode = request.getParameter("mode");

			String commentText = request.getParameter("commentText");

			
			
			int userId = Integer.parseInt(request.getParameter("userId"));
			int postId = Integer.parseInt(request.getParameter("postId"));
			Comment comment= new Comment(commentText, "");

			int commentId = service.addComment(comment, userId, postId);
			
			if(commentId > 0){
				service.incrPostThx(postId);
			}
				
			if("android".equals(mode)){
				
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("commentId", commentId);
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
