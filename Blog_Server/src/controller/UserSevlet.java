package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yc.dao.BeanUtils;

import bean.User;
import biz.BizException;
import biz.UserBiz;

/**
 * �û�Servlet ���� ��¼�� ע�ᣬ ��ѯ���˳��� �������룬ʹ��op�ֶα�ʶҵ���������
 */
@WebServlet("/user.s")
public class UserSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UserSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if("login".equals(op)) {
			login(request,response);
		}else if("query".equals(op)) {
			query(request,response);
		}else if("add".equals(op)) {
			add(request,response);
		}else if("find".equals(op)) {
			find(request,response);
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private void find(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		UserBiz ubiz =new UserBiz();
		User user = ubiz.findById(id);
		
		String userString = JSON.toJSONString(user);
		response.getWriter().append(userString);
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBiz ubiz =new UserBiz();
		User user = BeanUtils.asBean(request, User.class);
		try {
			ubiz.add(user);
			
		}catch(BizException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}finally {
			query(request,response);
		}
		
	}

	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBiz ubiz = new UserBiz();
		User user = BeanUtils.asBean(request,User.class);
		request.setAttribute("userList", ubiz.find(user));
		request.getRequestDispatcher("manage-user.jsp").forward(request, response);
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		
		//��ѯ���ݿ��ж��û��Ƿ����
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
			//ʧ��
			request.setAttribute("msg", "�û������������");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			//�ɹ�
			request.getSession().setAttribute("loginedUser",user);
			response.sendRedirect("index.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
