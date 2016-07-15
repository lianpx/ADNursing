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

import sun.tools.jar.resources.jar;

import com.ad.business.CommentBean;
import com.ad.business.PostBean;
import com.ad.business.UserBean;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 根据种类和评论数查看帖子的接口
 */

@WebServlet(urlPatterns = "/android/viewPostsByKindAndCommentNum.jsp")
public class ViewPostsByKindAndCommentNum extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String op = request.getParameter("op");
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();
		String post_kind = request.getParameter("postKind");
		

		List<PostBean> posts = service.getPostsByKind(post_kind);
		List<PostBean> results = service.getPostsByKind(post_kind);
		
		int[] comment_nums = new int[posts.size()];
		int temp = 0;
		for(PostBean post:posts) {
			comment_nums[temp] = post.getComment_num();
			temp++;
		}
		
        PostBean tmpBean1;
        PostBean tmpBean2;
       
        int size = comment_nums.length; // 数组大小   
        for (int i = 0; i < size - 1; i++) {   
            for (int j = i + 1; j < size; j++) {   
                if (comment_nums[i] < comment_nums[j]) { // 交换两数的位置   
                    temp = comment_nums[i];   
                    comment_nums[i] = comment_nums[j];   
                    comment_nums[j] = temp;
                    tmpBean1 = results.get(i);
                    tmpBean2 = results.get(j);
                    results.set(i, tmpBean2);
                    results.set(j, tmpBean1);
                }   
            }   
        }
        
        JSONArray jsonArray = new JSONArray(results);
		
		for(int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String owner="";
			try {
				owner = jsonObject.getString("owner");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int userId = service.getUserId(owner);
			UserBean userBean = service.getUser(userId);
			String userImgUrl = userBean.getImg_url();
			try {
				jsonArray.getJSONObject(i).put("userImgUrl", userImgUrl);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if ("android".equals(mode)) {
			response.getWriter().println(jsonArray.toString());
		} else if ("web".equals(mode)) {
			request.setAttribute("posts", results);
			request.getRequestDispatcher("/manage/viewPostByKind.jsp").forward(
					request, response);
		}
	}
	
    public  int[] bubbleSort(int[] numbers) {   

        return numbers;
    } 

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
