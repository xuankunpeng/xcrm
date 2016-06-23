package com.xcrm.system.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xcrm.system.user.entity.User;
import com.xcrm.system.user.service.UserService;
import com.xcrm.system.user.utils.GsonUtil;


public class AllServlet extends HttpServlet {
	static UserService userService;
	
	static{
		userService = new UserService();
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("."));
		if(action.equals("/addUser")){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String age = request.getParameter("age");
			String gender = request.getParameter("gender");
			User user=new User();
			user.setAge(Integer.valueOf(age));
			user.setGender(gender);
			user.setPassword(password);
			user.setUsername(username);
			try {
				userService.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println("success");
//			request.getRequestDispatcher("/system/user/listUserJqury.jsp").forward(request, response);
		}else if(action.equals("/check_number")){
			String number1 = request.getParameter("number");
			HttpSession session = 
				request.getSession();
			String number2 = (String)session.getAttribute("number");
			if(number1.equalsIgnoreCase(number2)){
				out.println("验证码正确");
			}else{
				out.println("验证码错误");
			}
			
		}else if(action.equals("/regist")){
			//加上验证代码,比如，要检查
			//用户名是否正确，验证码是否正确,
			//此处略。
			System.out.println("注册成功");
		}else if(action.equals("/getUsers")){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			PrintWriter out2 = response.getWriter();
			try {
				List<User> userList=userService.getUsers();
				out2.println(GsonUtil.toJson(userList));
				out2.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}else if(action.equals("/deleteuser")){
			String id = request.getParameter("id");
			try {
				userService.deleteUser(id);
//				request.getRequestDispatcher("/system/user/listUserJqury.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(action.equals("/edituser")){
			String id = request.getParameter("id");
			try {
				User user=userService.findById(id);
				request.setAttribute("result", user);
				request.getRequestDispatcher("/system/user/update1.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(action.equals("/updateUser")){
			User user=new User();
			user.setId(Integer.valueOf(request.getParameter("id")));
			user.setAge(Integer.valueOf(request.getParameter("age")));
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setGender(request.getParameter("gender"));
			try {
				userService.updateUser(user);
				User newuser=userService.findById(request.getParameter("id"));
				request.setAttribute("result", newuser);
				request.getRequestDispatcher("/system/user/update.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
