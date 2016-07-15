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

import com.ad.bean.Comment;
import com.ad.bean.Post;
import com.ad.service.BussinessService;
import com.ad.service.impl.BussinessServiceImpl;

/**
 * 添加评论的接口
 */
@WebServlet(urlPatterns = "/android/addComment.jsp")
public class AddCommentServlet extends HttpServlet {
		// 获取业务逻辑对象
		BussinessService service = new BussinessServiceImpl();

		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String mode = request.getParameter("mode");
			//response.setContentType("text/html");
			Integer postId = Integer.parseInt(request.getParameter("postId"));
			
			Integer commentId = -1;
			commentId = addComment(request);
			
			if(commentId > 0){
				service.incrPostThx(postId);
			}
			if("android".equals(mode)){

					// 添加成功
					if (commentId > 0) {
						response.getWriter().println("恭喜您，评论发布成功!");
					} else {
						response.getWriter().println("对不起，评论发布失败!");
					}
				

			}else if("web".equals(mode)){
				request.getRequestDispatcher("/manage/index.jsp").forward(
						request, response);			
			}
		}
		
		private int addComment(HttpServletRequest request) {		
			// 设置request编码，主要是为了处理普通输入框中的中文问题
			//request.setCharacterEncoding("gbk");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置文件的缓存路径
			factory.setRepository(new File("E:/ADNursing/temp/"));
			//上传文件的保存路径
			String path = "E:\\ADNursing\\image\\";
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// System.out.print("已经生成临时文件");

			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置上传文件大小的上限，-1表示无上限
			upload.setSizeMax(1000 * 1024 * 1024); //1000MB
			List items = new ArrayList();
			try {
				// 上传文件，并解析出所有的表单字段，包括普通字段和文件字段
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				System.out.println("文件上传发生错误" + e1.getMessage());
			}
			
			// 解析请求参数
			// 获取userId
			String owner = request.getParameter("owner");
			Integer userId = service.getUserId(owner);
			Integer postId = Integer.parseInt(request.getParameter("postId"));

			String commentText = null;
			String imgUrl = "";
			

			// 下面对每个字段进行处理，分普通字段和文件字段
			Iterator it = items.iterator();
			while (it.hasNext()) {
				DiskFileItem fileItem = (DiskFileItem) it.next();
				// 如果是普通字段
				if (fileItem.isFormField()) {
					try {
						if ("commentText".equals(fileItem.getFieldName())) {
							commentText = new String(fileItem.getString().getBytes(
									"iso8859-1"), "UTF-8");
						}
						System.out.println(fileItem.getFieldName()
								+ "   "
								+ fileItem.getName()
								+ "   "
								+ new String(fileItem.getString().getBytes(
										"iso8859-1"), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} 
				// 如果是文件字段
				else {
					System.out.println(fileItem.getFieldName() + "   "
							+ fileItem.getName() + "   "
							+ fileItem.isInMemory() + "    "
							+ fileItem.getContentType() + "   "
							+ fileItem.getSize());
					String filename = fileItem.getName();
					String url = "";
					if(filename != "") {
						url = path + makeFileName(filename);
						if (imgUrl != "") {
							imgUrl += "|";
						}
					}

					imgUrl += url;
									
					// 保存文件，其实就是把缓存里的数据写到目标路径下
					if (filename != null && fileItem.getSize() != 0) {
						File newFile = new File(url);
						try {
							fileItem.write(newFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("文件没有选择 或 文件内容为空");
					}
				}
			}

			Comment comment = new Comment(commentText, imgUrl);
			System.out.println(imgUrl);
			int commentId = service.addComment(comment, userId, postId);
			return commentId;
		}
		
		 /**
		  * @Method: makeFileName
		  * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
		  * @param filename 文件的原始名称
		  * @return uuid+"_"+文件的原始名称
		  */ 
		private String makeFileName(String filename){
		//为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
			return UUID.randomUUID().toString() + "_" + filename;
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}
}

