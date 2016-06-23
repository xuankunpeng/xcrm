package com.xcrm.system.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xcrm.system.user.entity.User;
import com.xcrm.system.user.utils.GsonUtil;

@SuppressWarnings("serial")
public class ListUserServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		try {
			List<User> userList = new ArrayList<User>();
			
			User user = new User();
			user.setId(1000);
			user.setUsername("username");
			
			userList.add(user);
			
			out.println(GsonUtil.toJson(userList));
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
