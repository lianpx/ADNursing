package com.ad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.business.CommentBean;
import com.ad.business.PostBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 删除评论的接口
 */

@WebServlet(urlPatterns = "/android/delComment.jsp")
public class DelCommentServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		Integer commentId = Integer.parseInt(request.getParameter("commentId"));
		CommentBean comment = service.getComment(commentId);
		
		int res = service.delCommentByUserIdAndCommentId(userId, commentId);
		service.descrPostThx(comment.getPost_id());
		
		if ("android".equals(mode)) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("commentId", res);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.getWriter().println(jsonObject.toString());
		} else if ("web".equals(mode)) {	
			request.getRequestDispatcher("/manage/viewOwnerComment.jsp").forward(
					request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
