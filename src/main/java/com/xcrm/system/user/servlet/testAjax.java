package com.xcrm.system.user.servlet;

import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
  
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject; 

public class testAjax extends HttpServlet {
	 private static final long serialVersionUID = 1L;  
	  
	    public void doGet(HttpServletRequest request, HttpServletResponse response)  
	            throws IOException, ServletException {  
	        response.setContentType("text/html;charset=utf-8");  
	        String account = request.getParameter("account");  
	          
	        JSONObject json = new JSONObject();  
	          
	        JSONArray array = new JSONArray();  
	        JSONObject member = null;  
	        for (int i = 0; i < 3; i++) {  
	            member = new JSONObject();  
	            member.put("name", "xiaohua"+i);  
	            member.put("age", 20+i);  
	            array.add(member);  
	        }  
	          
	        json.put("account", account);  
	        json.put("jsonArray", array);  
	          
	        PrintWriter pw = response.getWriter();   
	        pw.print(json.toString());  
	          
	        System.out.println("json array :"+array.toString());  
	        System.out.println("json object :"+json.toString());  
	          
	        pw.close();  
	    }  
	  
	    public void doPost(HttpServletRequest request, HttpServletResponse response)  
	            throws IOException, ServletException {  
	        this.doGet(request, response);  
	    }  
}
