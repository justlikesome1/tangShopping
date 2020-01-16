package com.woniuxy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.winiuxy.entitys.Users;
import com.woniuxy.daos.UserDao;
import com.woniuxy.tools.MD5TOOL;

import javafx.scene.control.Alert;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet({"/londing.do","/exit.do","/checkPwd.do","/updPwd.do","/showUsers.do","/delUser.do","/offUser.do","/onUser.do","/resetPwd.do","/addUsers.do"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String path = request.getServletPath();
		System.out.println(path);
		
		UserDao ud = new UserDao();
		
		
		if (path.equals("/londing.do")) {
			//获取用户输入的用户名与密码
			String usersName = request.getParameter("usersName");
			String usersPwd = request.getParameter("usersPwd");
			
			//通过输入的用户名在数据库中查找信息,判断是否存在
			boolean isTrue = false;
			try {
				isTrue = ud.isExist(usersName, MD5TOOL.getMD5String(usersPwd));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//存在跳转主界面
			if (isTrue) {
				
				//将用户名添加到session中
				HttpSession session = request.getSession();
				session.setAttribute("usersName", usersName);
				
				//将用户id添加到session中
				session.setAttribute("userId", ud.getUserId(usersName));
				
				//得到cookie值
				Cookie cookie = new Cookie("cookie", usersName);
				cookie.setMaxAge(6000);
				response.addCookie(cookie);
				
				//得到在线人数
				ServletContext context = request.getServletContext();
				int count = 0;
				if (context.getAttribute("online") != null) {
					count = (int)context.getAttribute("online");
				}
				count++;
				context.setAttribute("online", count);
				
				response.sendRedirect("/TangShopping/Jsps/index.jsp");
				
			}else {//不存在继续留在登录界面
			response.sendRedirect("/TangShopping/Jsps/landing.jsp");
			}
			
			
		}
		else if (path.equals("/exit.do")) {
			//移除当前用户
			HttpSession session = request.getSession();
			session.removeAttribute("usersName");
			
			//在线人数减1
			ServletContext context = request.getServletContext();
			if (context != null) {
				int count = (int)context.getAttribute("online");
				count--;
				context.setAttribute("context", count);
			}
			
			response.sendRedirect("/TangShopping/Jsps/landing.jsp");
			
		}
		else if (path.equals("/checkPwd.do")) {//加密
			HttpSession session = request.getSession();
			String userPwd = request.getParameter("oldPwd");
			int userId = (int) session.getAttribute("userId");
			
			try {
				boolean tag = ud.isTrue(userId, MD5TOOL.getMD5String(userPwd));
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.print(tag);
				out.flush();
				out.close();
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(path.equals("/updPwd.do")) {//修改后保存
			String userPwd = request.getParameter("userNewPwd");
			HttpSession session = request.getSession();
			int userId = (int) session.getAttribute("userId");
			
			try {
				ud.updatePwdByeId(userId, MD5TOOL.getMD5String(userPwd));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			out.print("<script>top.location.href='/TangShopping/Jsps/landing.jsp'</script>");
			out.flush();
			out.close();
//			response.sendRedirect("/TangShopping/Jsps/landing.jsp");
			
		}
		else if(path.equals("/showUsers.do")){
			request.setCharacterEncoding("UTF-8");
			
			List<Users> list = ud.getAllUser();

			request.setAttribute("users", list);
			
			request.getRequestDispatcher("Jsps/users.jsp").forward(request, response);
			
		}
		else if(path.equals("/onUser.do")){
			request.setCharacterEncoding("UTF-8");
			String uId = request.getParameter("uId");
			
			ud.onUser(Integer.parseInt(uId));
			
			response.sendRedirect("showUsers.do");
			
		}
		else if(path.equals("/offUser.do")){
			request.setCharacterEncoding("UTF-8");
			String uId = request.getParameter("uId");
			
			ud.offUser(Integer.parseInt(uId));
			
			response.sendRedirect("showUsers.do");
			
		}	
		else if(path.equals("/delUser.do")){
			
			String uId = request.getParameter("uId");
			
			ud.delUser(Integer.parseInt(uId));
			
			response.sendRedirect("showUsers.do");
			
		}	
		else if(path.equals("/resetPwd.do")){
			request.setCharacterEncoding("UTF-8");
			String uId = request.getParameter("uId");
			
			try {
				ud.resetPwd(Integer.parseInt(uId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.sendRedirect("showUsers.do");
			
		}		
		else if(path.equals("/addUsers.do")){
			
			request.setCharacterEncoding("UTF-8");
			String userName = request.getParameter("userName");
			String usersRole = request.getParameter("usersRole"); 
			Users user = new Users(userName, usersRole);	
			System.out.println(userName);
			System.out.println(usersRole);
			ud.addUser(user);
		
			response.sendRedirect("showUsers.do");
				
		}				
		
		
	}

}
