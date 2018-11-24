package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import biz.BizException;
import biz.UserBiz;

@WebServlet("/login.s")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//接受参数
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		
		//查询数据库判断用户是否存在
		UserBiz ubiz = new UserBiz();
		User user = null;
		
		try {
			user = ubiz.login(username, userpwd);
		}catch (BizException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		if(user==null) {
			//失败
			request.setAttribute("msg", "用户名或密码错误！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			//成功
			request.getSession().setAttribute("loginedUser",user);
			response.sendRedirect("index.jsp");
		}
	} 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		doGet(request,response);
	}
	

}